package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderEvent

internal sealed interface CalendarEvent : WonderEvent {

    object GetCurrentYearMonth : CalendarEvent
    object GetCurrentCalendar : CalendarEvent
    data class UpdateCurrentYearMonth(val yearMonth: String) : CalendarEvent
}
