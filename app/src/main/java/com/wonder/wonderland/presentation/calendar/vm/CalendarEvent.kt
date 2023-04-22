package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderEvent

internal sealed interface CalendarEvent : WonderEvent {

    object GetCurrentMonth : CalendarEvent
}
