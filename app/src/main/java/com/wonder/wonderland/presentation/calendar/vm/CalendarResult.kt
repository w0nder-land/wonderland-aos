package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderResult
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo

internal sealed interface CalendarResult : WonderResult {

    data class CurrentMonth(val calendarInfo: CalendarInfo) : CalendarResult
}
