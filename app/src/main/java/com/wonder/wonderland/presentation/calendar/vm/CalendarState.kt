package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderState
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo

internal data class CalendarState(
    override val isLoading: Boolean = true,
    override val hasError: Boolean = false,
    val calendarInfo: CalendarInfo = CalendarInfo()
) : WonderState
