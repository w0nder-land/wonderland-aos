package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderResult
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo

internal sealed interface CalendarResult : WonderResult {

    data class CurrentYearMonth(
        val currentYearMonth: String,
        val yearMonthItems: List<String>
    ) : CalendarResult

    data class CurrentCalendar(val calendarInfo: CalendarInfo) : CalendarResult

    data class UpdateYearMonth(val currentYearMonth: String) : CalendarResult
}
