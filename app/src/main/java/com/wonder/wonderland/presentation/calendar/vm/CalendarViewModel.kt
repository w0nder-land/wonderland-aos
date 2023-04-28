package com.wonder.wonderland.presentation.calendar.vm

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
        filterIsInstance<CalendarEvent.UpdateCurrentYearMonth>().toUpdateCurrentYearMonthResult(),
        filterIsInstance<CalendarEvent.ClickFestival>().toClickFestivalResult()
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
                state.copy(
                    isLoading = true,
                    currentYearMonth = currentYearMonth
                )
            }
            else -> state
        }
    }

    override fun Flow<CalendarResult>.toEffects(): Flow<CalendarEffect> = merge(
        filterIsInstance<CalendarResult.ClickFestival>().toClickFestivalEffect()
    )

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

    private fun Flow<CalendarEvent.ClickFestival>.toClickFestivalResult(): Flow<CalendarResult> =
        mapLatest {
            CalendarResult.ClickFestival(festivalId = it.festivalId)
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

    private fun Flow<CalendarResult.ClickFestival>.toClickFestivalEffect(): Flow<CalendarEffect> =
        mapLatest {
            CalendarEffect.MoveFestival(it.festivalId)
        }
}
