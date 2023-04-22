package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderState

internal data class CalendarState(
    override val isLoading: Boolean = false,
    override val hasError: Boolean = false,
    val currentMonth: String = "2023년 04월",
    val startDyOfWeek: String = "수",
) : WonderState
