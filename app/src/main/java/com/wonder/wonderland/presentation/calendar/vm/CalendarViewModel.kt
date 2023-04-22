package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderViewModel
import com.wonder.component.util.getDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
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
            is CalendarResult.CurrentMonth -> state.copy(currentMonth = currentMonth)
        }
    }

    private fun Flow<CalendarEvent.GetCurrentMonth>.toGetCurrentMonthResult(): Flow<CalendarResult> =
        mapLatest {
            CalendarResult.CurrentMonth(currentMonth = getCurrentMonth())
        }

    private fun getCurrentMonth(): String = getDate("yyyy년 M월")
}
