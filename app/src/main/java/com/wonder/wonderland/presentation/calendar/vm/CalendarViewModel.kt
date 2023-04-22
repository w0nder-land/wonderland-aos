package com.wonder.wonderland.presentation.calendar.vm

import com.imaec.model.FestivalInfo
import com.wonder.base.WonderViewModel
import com.wonder.component.util.getCurrentMonth
import com.wonder.component.util.toCalendar
import com.wonder.component.util.toDate
import com.wonder.component.util.toDay
import com.wonder.wonderland.presentation.calendar.model.CalendarDayInfo
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo
import com.wonder.wonderland.presentation.calendar.model.FestivalDay
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
internal class CalendarViewModel @Inject constructor() :
    WonderViewModel<CalendarEvent, CalendarResult, CalendarState, CalendarEffect>(CalendarState()) {

    init {
        processEvent(CalendarEvent.GetCurrentMonth)
    }

    override fun Flow<CalendarEvent>.toResults(): Flow<CalendarResult> = merge(
        filterIsInstance<CalendarEvent.GetCurrentMonth>().toGetCurrentMonthResult()
    )

    override fun CalendarResult.reduce(state: CalendarState): CalendarState {
        return when (this) {
            is CalendarResult.CurrentMonth -> {
                state.copy(
                    isLoading = false,
                    calendarInfo = calendarInfo
                )
            }
        }
    }

    private fun Flow<CalendarEvent.GetCurrentMonth>.toGetCurrentMonthResult(): Flow<CalendarResult> =
        mapLatest {
            val calendarInfo = withContext(Dispatchers.Default) {
                getCalendarInfo(getFestivals())
            }
            CalendarResult.CurrentMonth(
                calendarInfo = calendarInfo
            )
        }

    private fun getFestivals(): List<FestivalInfo> {
        return listOf(
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
        )
    }
}

const val checkName = "7"
