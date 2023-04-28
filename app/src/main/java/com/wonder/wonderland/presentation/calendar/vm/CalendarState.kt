package com.wonder.wonderland.presentation.calendar.vm

import androidx.compose.foundation.lazy.grid.LazyGridState
import com.wonder.base.WonderState
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo

internal data class CalendarState(
    override val isLoading: Boolean = true,
    override val hasError: Boolean = false,
    val calendarGridState: LazyGridState = LazyGridState(),
    val currentYearMonth: String = "",
    val yearMonthItems: List<String> = emptyList(),
    val calendarInfo: CalendarInfo = CalendarInfo()
) : WonderState
