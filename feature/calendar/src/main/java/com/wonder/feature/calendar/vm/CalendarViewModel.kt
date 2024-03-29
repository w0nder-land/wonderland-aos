package com.wonder.feature.calendar.vm

import androidx.lifecycle.viewModelScope
import com.wonder.model.festival.FestivalAgeCountVo
import com.wonder.model.festival.FestivalAgeType
import com.wonder.model.festival.FestivalCategoryCountVo
import com.wonder.model.festival.FestivalCategoryType
import com.wonder.model.festival.FestivalRegionCountVo
import com.wonder.model.festival.FestivalRegionType
import com.wonder.model.festival.FestivalStateCountVo
import com.wonder.model.festival.FestivalStateType
import com.wonder.model.festival.toVo
import com.wonder.base.WonderViewModel
import com.wonder.component.util.addMonth
import com.wonder.component.util.dayOfMonth
import com.wonder.component.util.getCurrentYearMonth
import com.wonder.component.util.toCalendar
import com.wonder.component.util.toDate
import com.wonder.component.util.toDateString
import com.wonder.domain.model.festival.FestivalItem
import com.wonder.domain.usecase.calendar.GetCalendarInfoUseCase
import com.wonder.domain.usecase.calendar.SaveCalendarInfoParam
import com.wonder.domain.usecase.calendar.SaveCalendarInfoUseCase
import com.wonder.domain.usecase.festival.SearchFestivalParam
import com.wonder.domain.usecase.festival.SearchFestivalsUseCase
import com.wonder.feature.calendar.filter.CalendarFilter
import com.wonder.feature.calendar.filter.isSelected
import com.wonder.feature.calendar.model.CalendarInfoVo
import com.wonder.feature.calendar.model.toDomain
import com.wonder.feature.calendar.model.toVo
import com.wonder.feature.calendar.util.getCalendarInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
internal class CalendarViewModel @Inject constructor(
    private val searchFestivalsUseCase: SearchFestivalsUseCase,
    private val getCalendarInfoUseCase: GetCalendarInfoUseCase,
    private val saveCalendarInfoUseCase: SaveCalendarInfoUseCase,
) : WonderViewModel<CalendarEvent, CalendarResult, CalendarState, CalendarEffect>(CalendarState()) {

    override fun Flow<CalendarEvent>.toResults(): Flow<CalendarResult> = merge(
        filterIsInstance<CalendarEvent.Loading>().toLoadingResult(),
        filterIsInstance<CalendarEvent.GetCurrentYearMonth>().toGetCurrentYearMonthResult(),
        filterIsInstance<CalendarEvent.SearchFestivals>().toGetCalendarInfoFromLocalResult(),
        filterIsInstance<CalendarEvent.SearchRemoteFestivals>().toSearchRemoteFestivalsResult(),
        filterIsInstance<CalendarEvent.UpdateCurrentYearMonth>().toUpdateCurrentYearMonthResult(),
        filterIsInstance<CalendarEvent.UpdateInterest>().toUpdateInterestResult(),
        filterIsInstance<CalendarEvent.ClickFestival>().toClickFestivalResult(),
        filterIsInstance<CalendarEvent.ClickCategoryFilterItem>().toClickCategoryFilterItemResult(),
        filterIsInstance<CalendarEvent.ClickStateFilterItem>().toClickStateFilterItemResult(),
        filterIsInstance<CalendarEvent.ClickRegionFilterItem>().toClickRegionFilterItemResult(),
        filterIsInstance<CalendarEvent.ClickAgeFilterItem>().toClickAgeFilterItemResult(),
        filterIsInstance<CalendarEvent.ClearFilter>().toClearFilterResult(),
    )

    override fun CalendarResult.reduce(state: CalendarState): CalendarState {
        return when (this) {
            is CalendarResult.Loading -> state.copy(isLoading = isLoading)
            is CalendarResult.CurrentYearMonth -> {
                state.copy(
                    currentYearMonth = currentYearMonth,
                    yearMonthItems = yearMonthItems
                )
            }
            is CalendarResult.CurrentCalendar -> {
                state.copy(
                    isLoading = isLoading,
                    hasError = false,
                    calendarInfo = calendarInfo ?: state.calendarInfo,
                    categoryFilters = categoryFilters,
                    stateFilters = stateFilters,
                    regionFilters = regionFilters,
                    ageFilters = ageFilters,
                    selectedCategoryFilters = selectedCategoryFilters,
                    selectedStateFilters = selectedStateFilters,
                    selectedRegionFilters = selectedRegionFilters,
                    selectedAgeFilters = selectedAgeFilters
                )
            }
            is CalendarResult.UpdateYearMonth -> state.copy(currentYearMonth = currentYearMonth)
            is CalendarResult.UpdateInterest -> {
                state.copy(
                    isInterest = isInterest,
                    calendarInfo = CalendarInfoVo()
                )
            }
            is CalendarResult.ClickCategoryFilterItem -> {
                state.copy(categoryFilters = categoryFilters)
            }
            is CalendarResult.ClickStateFilterItem -> state.copy(stateFilters = stateFilters)
            is CalendarResult.ClickRegionFilterItem -> state.copy(regionFilters = regionFilters)
            is CalendarResult.ClickAgeFilterItem -> state.copy(ageFilters = ageFilters)
            is CalendarResult.ClearFilter -> {
                state.copy(
                    categoryFilters = categoryFilters,
                    stateFilters = stateFilters,
                    regionFilters = regionFilters,
                    ageFilters = ageFilters,
                )
            }
            is CalendarResult.ClickFestival -> state
            is CalendarResult.Error -> {
                state.copy(
                    hasError = true,
                    categoryFilters = categoryFilters,
                    stateFilters = stateFilters,
                    regionFilters = regionFilters,
                    ageFilters = ageFilters,
                )
            }
        }
    }

    override fun Flow<CalendarResult>.toEffects(): Flow<CalendarEffect> = merge(
        filterIsInstance<CalendarResult.ClickFestival>().toClickFestivalEffect()
    )

    private fun Flow<CalendarEvent.Loading>.toLoadingResult(): Flow<CalendarResult> =
        mapLatest { CalendarResult.Loading(it.isLoading) }

    private fun Flow<CalendarEvent.GetCurrentYearMonth>.toGetCurrentYearMonthResult() =
        mapLatest {
            val currentYearMonth = getCurrentYearMonth()
            val yearMonthItems = getYearMonthItems()

            CalendarResult.CurrentYearMonth(
                currentYearMonth = currentYearMonth,
                yearMonthItems = yearMonthItems
            )
        }

    private fun Flow<CalendarEvent.SearchFestivals>.toGetCalendarInfoFromLocalResult() =
        mapLatest {
            processEvent(CalendarEvent.SearchRemoteFestivals(yearMonth = it.yearMonth))

            val calendarInfo = if (states.value.isFilterSelected()) {
                null
            } else {
                withContext(Dispatchers.IO) {
                    getCalendarInfoUseCase(it.yearMonth)
                }?.toVo()?.copy(
                    today = Calendar.getInstance().dayOfMonth()
                )
            }
            CalendarResult.CurrentCalendar(
                isLoading = it.isLoading && calendarInfo == null,
                calendarInfo = calendarInfo,
                categoryFilters = states.value.categoryFilters,
                stateFilters = states.value.stateFilters,
                regionFilters = states.value.regionFilters,
                ageFilters = states.value.ageFilters,
                selectedCategoryFilters = states.value.categoryFilters,
                selectedStateFilters = states.value.stateFilters,
                selectedRegionFilters = states.value.regionFilters,
                selectedAgeFilters = states.value.ageFilters,
            )
        }

    private fun Flow<CalendarEvent.SearchRemoteFestivals>.toSearchRemoteFestivalsResult() =
        mapLatest {
            val categoryCodes = states.value.categoryFilters
                .filter { it.isSelected }.map { it.code }
            val stateCodes = states.value.stateFilters
                .filter { it.isSelected }.map { it.code }
            val regionCodes = states.value.regionFilters
                .filter { it.isSelected }.map { it.code }
            val ageCodes = states.value.ageFilters
                .filter { it.isSelected }.map { it.code }
            val festival = searchFestivalsUseCase(
                SearchFestivalParam(
                    date = it.yearMonth.toDate("yyyy년 M월").toDateString("yyyy-MM"),
                    likeStatus = states.value.isInterest,
                    category = categoryCodes,
                    state = stateCodes,
                    region = regionCodes,
                    age = ageCodes,
                )
            ) ?: return@mapLatest CalendarResult.Error(
                categoryFilters = states.value.selectedCategoryFilters,
                stateFilters = states.value.selectedStateFilters,
                regionFilters = states.value.selectedRegionFilters,
                ageFilters = states.value.selectedAgeFilters
            )
            val festivals = festival.festivals.map(FestivalItem::toVo)
            val festivalFilter = festival.filterItems.toVo()
            val calendarInfo = withContext(Dispatchers.Default) {
                getCalendarInfo(
                    festivals = festivals,
                    calendar = it.yearMonth.toDate("yyyy년 M월").toCalendar()
                )
            }
            val categoryFilters = getCategoryFilters(festivalFilter.category, categoryCodes)
            val stateFilters = getStateFilters(festivalFilter.state, stateCodes)
            val regionFilters = getRegionFilters(festivalFilter.region, regionCodes)
            val ageFilters = getAgeFilters(festivalFilter.age, ageCodes)

            saveCalendarInfo(
                yearMonth = it.yearMonth,
                calendarInfo = calendarInfo,
                categoryFilters = categoryFilters,
                stateFilters = stateFilters,
                regionFilters = regionFilters,
                ageFilters = ageFilters,
            )

            CalendarResult.CurrentCalendar(
                isLoading = false,
                calendarInfo = calendarInfo,
                categoryFilters = categoryFilters,
                stateFilters = stateFilters,
                regionFilters = regionFilters,
                ageFilters = ageFilters,
                selectedCategoryFilters = categoryFilters,
                selectedStateFilters = stateFilters,
                selectedRegionFilters = regionFilters,
                selectedAgeFilters = ageFilters,
            )
        }

    private fun Flow<CalendarEvent.UpdateCurrentYearMonth>.toUpdateCurrentYearMonthResult() =
        mapLatest {
            CalendarResult.UpdateYearMonth(currentYearMonth = it.yearMonth)
        }

    private fun Flow<CalendarEvent.UpdateInterest>.toUpdateInterestResult(): Flow<CalendarResult> =
        mapLatest {
            CalendarResult.UpdateInterest(isInterest = it.isInterest)
        }

    private fun Flow<CalendarEvent.ClickFestival>.toClickFestivalResult(): Flow<CalendarResult> =
        mapLatest {
            CalendarResult.ClickFestival(festivalId = it.festivalId)
        }

    private fun Flow<CalendarEvent.ClickCategoryFilterItem>.toClickCategoryFilterItemResult() =
        mapLatest {
            CalendarResult.ClickCategoryFilterItem(
                categoryFilters = getClickedFilters(
                    title = it.categoryFilter.title,
                    filters = states.value.categoryFilters
                )
            )
        }

    private fun Flow<CalendarEvent.ClickStateFilterItem>.toClickStateFilterItemResult() =
        mapLatest {
            CalendarResult.ClickStateFilterItem(
                stateFilters = getClickedFilters(
                    title = it.stateFilter.title,
                    filters = states.value.stateFilters
                )
            )
        }

    private fun Flow<CalendarEvent.ClickRegionFilterItem>.toClickRegionFilterItemResult() =
        mapLatest {
            CalendarResult.ClickRegionFilterItem(
                regionFilters = getClickedFilters(
                    title = it.regionFilter.title,
                    filters = states.value.regionFilters
                )
            )
        }

    private fun Flow<CalendarEvent.ClickAgeFilterItem>.toClickAgeFilterItemResult() =
        mapLatest {
            CalendarResult.ClickAgeFilterItem(
                ageFilters = getClickedFilters(
                    title = it.ageFilter.title,
                    filters = states.value.ageFilters
                )
            )
        }

    private fun Flow<CalendarEvent.ClearFilter>.toClearFilterResult() =
        mapLatest {
            CalendarResult.ClearFilter(
                categoryFilters = getClickedFilters(
                    title = "전체",
                    filters = states.value.categoryFilters
                ),
                stateFilters = getClickedFilters(
                    title = "전체",
                    filters = states.value.stateFilters
                ),
                regionFilters = getClickedFilters(
                    title = "전체",
                    filters = states.value.regionFilters
                ),
                ageFilters = getClickedFilters(
                    title = "전체",
                    filters = states.value.ageFilters
                )
            )
        }

    private fun Flow<CalendarResult.ClickFestival>.toClickFestivalEffect(): Flow<CalendarEffect> =
        mapLatest {
            CalendarEffect.MoveFestival(it.festivalId)
        }

    private val yearMonthRange = 2000
    private fun getYearMonthItems(): List<String> {
        val monthItems = mutableListOf<String>()
        val calendar = Calendar.getInstance().addMonth(-yearMonthRange / 2)
        repeat(yearMonthRange) {
            calendar.addMonth(1)
            monthItems.add("${calendar[Calendar.YEAR]}년 ${calendar[Calendar.MONTH] + 1}월")
        }

        return monthItems
    }

    private fun getCategoryFilters(
        categoryCount: FestivalCategoryCountVo,
        categoryCodes: List<String?>,
    ): List<CalendarFilter> =
        listOf(
            CalendarFilter(
                title = "전체",
                count = categoryCount.run { F + H + I + V },
                code = null,
                isSelected = categoryCodes.any { it == null } || categoryCodes.isEmpty()
            ),
            CalendarFilter(
                title = "뮤직 페스티벌",
                count = categoryCount.F,
                code = FestivalCategoryType.F.name,
                isSelected = categoryCodes.any { it == FestivalCategoryType.F.name }
            ),
            CalendarFilter(
                title = "인디/록",
                count = categoryCount.I,
                code = FestivalCategoryType.I.name,
                isSelected = categoryCodes.any { it == FestivalCategoryType.I.name }
            ),
            CalendarFilter(
                title = "내한 공연",
                count = categoryCount.V,
                code = FestivalCategoryType.V.name,
                isSelected = categoryCodes.any { it == FestivalCategoryType.V.name }
            ),
            CalendarFilter(
                title = "힙합/EDM",
                count = categoryCount.H,
                code = FestivalCategoryType.H.name,
                isSelected = categoryCodes.any { it == FestivalCategoryType.H.name }
            )
        )

    private fun getStateFilters(
        stateCount: FestivalStateCountVo,
        stateCodes: List<String?>,
    ): List<CalendarFilter> =
        listOf(
            CalendarFilter(
                title = "전체",
                count = stateCount.run { Y + W + N },
                code = null,
                isSelected = stateCodes.any { it == null } || stateCodes.isEmpty()
            ),
            CalendarFilter(
                title = "진행 중인 축제",
                count = stateCount.Y,
                code = FestivalStateType.Y.name,
                isSelected = stateCodes.any { it == FestivalStateType.Y.name }
            ),
            CalendarFilter(
                title = "시작 예정 축제",
                count = stateCount.W,
                code = FestivalStateType.W.name,
                isSelected = stateCodes.any { it == FestivalStateType.W.name }
            ),
            CalendarFilter(
                title = "종료된 축제",
                count = stateCount.N,
                code = FestivalStateType.N.name,
                isSelected = stateCodes.any { it == FestivalStateType.N.name }
            )
        )

    private fun getRegionFilters(
        regionCount: FestivalRegionCountVo,
        regionCodes: List<String?>
    ): List<CalendarFilter> =
        listOf(
            CalendarFilter(
                title = "전체",
                count = regionCount.run {
                    SEL + DHK + HND + GGI + INC +
                        DEJ + DEG + GWJ + BUS + ULS +
                        CHC + GKS + JEL + KWO + JEU
                },
                code = null,
                isSelected = regionCodes.any { it == null } || regionCodes.isEmpty()
            ),
            CalendarFilter(
                title = "서울",
                count = regionCount.SEL,
                code = FestivalRegionType.SEL.name,
                isSelected = regionCodes.any { it == FestivalRegionType.SEL.name }
            ),
            CalendarFilter(
                title = "대학로",
                count = regionCount.DHK,
                code = FestivalRegionType.DHK.name,
                isSelected = regionCodes.any { it == FestivalRegionType.DHK.name }
            ),
            CalendarFilter(
                title = "홍대",
                count = regionCount.HND,
                code = FestivalRegionType.HND.name,
                isSelected = regionCodes.any { it == FestivalRegionType.HND.name }
            ),
            CalendarFilter(
                title = "경기",
                count = regionCount.GGI,
                code = FestivalRegionType.GGI.name,
                isSelected = regionCodes.any { it == FestivalRegionType.GGI.name }
            ),
            CalendarFilter(
                title = "인천",
                count = regionCount.INC,
                code = FestivalRegionType.INC.name,
                isSelected = regionCodes.any { it == FestivalRegionType.INC.name }
            ),
            CalendarFilter(
                title = "대전",
                count = regionCount.DEJ,
                code = FestivalRegionType.DEJ.name,
                isSelected = regionCodes.any { it == FestivalRegionType.DEJ.name }
            ),
            CalendarFilter(
                title = "대구",
                count = regionCount.DEG,
                code = FestivalRegionType.DEG.name,
                isSelected = regionCodes.any { it == FestivalRegionType.DEG.name }
            ),
            CalendarFilter(
                title = "광주",
                count = regionCount.GWJ,
                code = FestivalRegionType.GWJ.name,
                isSelected = regionCodes.any { it == FestivalRegionType.GWJ.name }
            ),
            CalendarFilter(
                title = "부산",
                count = regionCount.BUS,
                code = FestivalRegionType.BUS.name,
                isSelected = regionCodes.any { it == FestivalRegionType.BUS.name }
            ),
            CalendarFilter(
                title = "울산",
                count = regionCount.ULS,
                code = FestivalRegionType.ULS.name,
                isSelected = regionCodes.any { it == FestivalRegionType.ULS.name }
            ),
            CalendarFilter(
                title = "충청",
                count = regionCount.CHC,
                code = FestivalRegionType.CHC.name,
                isSelected = regionCodes.any { it == FestivalRegionType.CHC.name }
            ),
            CalendarFilter(
                title = "경상",
                count = regionCount.GKS,
                code = FestivalRegionType.GKS.name,
                isSelected = regionCodes.any { it == FestivalRegionType.GKS.name }
            ),
            CalendarFilter(
                title = "전라",
                count = regionCount.JEL,
                code = FestivalRegionType.JEL.name,
                isSelected = regionCodes.any { it == FestivalRegionType.JEL.name }
            ),
            CalendarFilter(
                title = "강원",
                count = regionCount.KWO,
                code = FestivalRegionType.KWO.name,
                isSelected = regionCodes.any { it == FestivalRegionType.KWO.name }
            ),
            CalendarFilter(
                title = "제주",
                count = regionCount.JEU,
                code = FestivalRegionType.JEU.name,
                isSelected = regionCodes.any { it == FestivalRegionType.JEU.name }
            )
        )

    private fun getAgeFilters(
        ageCount: FestivalAgeCountVo,
        ageCodes: List<String?>
    ): List<CalendarFilter> =
        listOf(
            CalendarFilter(
                title = "전체",
                count = ageCount.run { GR001 + GR013 + GR999 },
                code = null,
                isSelected = ageCodes.any { it == null } || ageCodes.isEmpty()
            ),
            CalendarFilter(
                title = "전체 관람가",
                count = ageCount.GR001,
                code = FestivalAgeType.GR001.name,
                isSelected = ageCodes.any { it == FestivalAgeType.GR001.name }
            ),
            CalendarFilter(
                title = "만 12세 이상",
                count = ageCount.GR013,
                code = FestivalAgeType.GR013.name,
                isSelected = ageCodes.any { it == FestivalAgeType.GR013.name }
            ),
            CalendarFilter(
                title = "만 19세 이상",
                count = ageCount.GR999,
                code = FestivalAgeType.GR999.name,
                isSelected = ageCodes.any { it == FestivalAgeType.GR999.name }
            )
        )

    private fun getClickedFilters(
        title: String,
        filters: List<CalendarFilter>
    ) = if (title == "전체") {
        filters.map { filter ->
            filter.copy(isSelected = filter.title == title)
        }
    } else {
        filters.map { filter ->
            if (filter.title == title) {
                filter.copy(isSelected = !filter.isSelected)
            } else {
                filter
            }
        }.run {
            if (filterNot { it.title == "전체" }.all { it.isSelected } ||
                filterNot { it.title == "전체" }.all { !it.isSelected }
            ) {
                map { filter ->
                    filter.copy(isSelected = filter.title == "전체")
                }
            } else {
                map { filter ->
                    filter.copy(
                        isSelected = if (filter.title == "전체") false else filter.isSelected
                    )
                }
            }
        }
    }

    private fun saveCalendarInfo(
        yearMonth: String,
        calendarInfo: CalendarInfoVo,
        categoryFilters: List<CalendarFilter>,
        stateFilters: List<CalendarFilter>,
        regionFilters: List<CalendarFilter>,
        ageFilters: List<CalendarFilter>,
    ) {
        val isFilterSelected = categoryFilters.isSelected() ||
            stateFilters.isSelected() ||
            regionFilters.isSelected() ||
            ageFilters.isSelected()
        if (isFilterSelected) return

        viewModelScope.launch(Dispatchers.IO) {
            saveCalendarInfoUseCase(
                SaveCalendarInfoParam(
                    yearMonth = yearMonth,
                    calendarInfo = calendarInfo.toDomain()
                )
            )
        }
    }
}
