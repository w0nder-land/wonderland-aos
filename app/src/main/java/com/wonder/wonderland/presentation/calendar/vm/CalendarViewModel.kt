package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderViewModel
import com.wonder.component.util.getCurrentMonth
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
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
            is CalendarResult.CurrentMonth -> state.copy(calendarInfo = calendarInfo)
        }
    }

    private fun Flow<CalendarEvent.GetCurrentMonth>.toGetCurrentMonthResult(): Flow<CalendarResult> =
        mapLatest {
            CalendarResult.CurrentMonth(calendarInfo = getCalendarInfo())
        }

    private fun getCalendarInfo(): CalendarInfo {
        // 현재 월
        val calendar = Calendar.getInstance()
        val today = calendar[Calendar.DAY_OF_MONTH]
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        // 전 월
        calendar.set(Calendar.MONTH, calendar[Calendar.MONTH])
        val beforeMonthLastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        return CalendarInfo(
            currentMonth = getCurrentMonth(),
            today = today,
            firstDayOfWeek = firstDayOfWeek,
            lastDayOfMonth = lastDayOfMonth,
            beforeMonthLastDayOfMonth = beforeMonthLastDayOfMonth,
        )
    }
}
