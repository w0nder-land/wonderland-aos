package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderResult

internal sealed interface CalendarResult : WonderResult {

    data class CurrentMonth(val currentMonth: String) : CalendarResult
}
