package com.wonder.wonderland.presentation.calendar.vm

import com.imaec.model.FestivalInfo
import com.imaec.model.festival.toVo
import com.wonder.base.WonderViewModel
import com.wonder.component.util.addMonth
import com.wonder.component.util.getCurrentYearMonth
import com.wonder.component.util.toCalendar
import com.wonder.component.util.toDate
import com.wonder.component.util.toDateString
import com.wonder.domain.usecase.festival.SearchFestivalParam
import com.wonder.domain.usecase.festival.SearchFestivalsUseCase
import com.wonder.wonderland.presentation.calendar.util.getCalendarInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
internal class CalendarViewModel @Inject constructor(
    private val searchFestivalsUseCase: SearchFestivalsUseCase
) : WonderViewModel<CalendarEvent, CalendarResult, CalendarState, CalendarEffect>(CalendarState()) {

    override fun Flow<CalendarEvent>.toResults(): Flow<CalendarResult> = merge(
        filterIsInstance<CalendarEvent.GetCurrentYearMonth>().toGetCurrentYearMonthResult(),
        filterIsInstance<CalendarEvent.SearchFestivals>().toSearchFestivalsResult(),
        filterIsInstance<CalendarEvent.UpdateCurrentYearMonth>().toUpdateCurrentYearMonthResult()
    )

    override fun CalendarResult.reduce(state: CalendarState): CalendarState {
        return when (this) {
            is CalendarResult.CurrentYearMonth -> {
                state.copy(
                    currentYearMonth = currentYearMonth,
                    yearMonthItems = yearMonthItems
                )
            }
            is CalendarResult.CurrentCalendar -> {
                state.copy(
                    isLoading = false,
                    calendarInfo = calendarInfo
                )
            }
            is CalendarResult.UpdateYearMonth -> {
                state.copy(currentYearMonth = currentYearMonth)
            }
        }
    }

    private fun Flow<CalendarEvent.GetCurrentYearMonth>.toGetCurrentYearMonthResult(): Flow<CalendarResult> =
        mapLatest {
            val currentYearMonth = getCurrentYearMonth()
            val yearMonthItems = getYearMonthItems()

            CalendarResult.CurrentYearMonth(
                currentYearMonth = currentYearMonth,
                yearMonthItems = yearMonthItems
            )
        }

    private fun Flow<CalendarEvent.SearchFestivals>.toSearchFestivalsResult(): Flow<CalendarResult> =
        mapLatest {
            val festivals = searchFestivalsUseCase(
                SearchFestivalParam(
                    date = it.yearMonth.toDate("yyyy년 M월").toDateString("yyyy-MM")
                )
            ).festivals.map { festivalItem ->
                festivalItem.toVo()
            }
            val calendarInfo = withContext(Dispatchers.Default) {
                getCalendarInfo(
                    festivals = festivals,
                    calendar = it.yearMonth.toDate("yyyy년 M월").toCalendar()
                )
            }
            CalendarResult.CurrentCalendar(calendarInfo = calendarInfo)
        }

    private fun Flow<CalendarEvent.UpdateCurrentYearMonth>.toUpdateCurrentYearMonthResult(): Flow<CalendarResult> =
        mapLatest {
            CalendarResult.UpdateYearMonth(currentYearMonth = it.yearMonth)
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

    private fun getFestivals(): List<FestivalInfo> {
        return listOf(
            FestivalInfo(
                name = "0",
                startDate = "2023.02.26",
                endDate = "2023.03.04"
            ),
            FestivalInfo(
                name = "1",
                startDate = "2023.04.01",
                endDate = "2023.04.02"
            ),
            FestivalInfo(
                name = "2",
                startDate = "2023.04.01",
                endDate = "2023.04.6"
            ),
            FestivalInfo(
                name = "3",
                startDate = "2023.04.1",
                endDate = "2023.04.4"
            ),
            FestivalInfo(
                name = "4",
                startDate = "2023.04.1",
                endDate = "2023.04.5"
            ),
            FestivalInfo(
                name = "5",
                startDate = "2023.04.1",
                endDate = "2023.04.3"
            ),
            FestivalInfo(
                name = "6",
                startDate = "2023.04.1",
                endDate = "2023.04.1"
            ),
            FestivalInfo(
                name = "7",
                startDate = "2023.04.13",
                endDate = "2023.04.17"
            ),
            FestivalInfo(
                name = "8",
                startDate = "2023.04.20",
                endDate = "2023.04.21"
            ),
            FestivalInfo(
                name = "9",
                startDate = "2023.04.15",
                endDate = "2023.04.20"
            ),
            FestivalInfo(
                name = "10",
                startDate = "2023.04.19",
                endDate = "2023.04.24"
            ),
            FestivalInfo(
                name = "11",
                startDate = "2023.04.1",
                endDate = "2023.04.14"
            ),
            FestivalInfo(
                name = "12",
                startDate = "2023.04.3",
                endDate = "2023.04.7"
            ),
            FestivalInfo(
                name = "13",
                startDate = "2023.04.26",
                endDate = "2023.04.26"
            ),
            FestivalInfo(
                name = "14",
                startDate = "2023.04.23",
                endDate = "2023.04.23"
            ),
            FestivalInfo(
                name = "15",
                startDate = "2023.04.24",
                endDate = "2023.04.24"
            ),
            FestivalInfo(
                name = "16",
                startDate = "2023.04.25",
                endDate = "2023.04.25"
            ),
            FestivalInfo(
                name = "17",
                startDate = "2023.03.28",
                endDate = "2023.04.1"
            ),
            FestivalInfo(
                name = "18",
                startDate = "2023.02.28",
                endDate = "2023.03.1"
            ),
        )
    }
}
