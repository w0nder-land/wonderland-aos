package com.wonder.wonderland.presentation.calendar.model

internal data class FestivalDay(
    val festivalName: String,
    val day: Int,
    val isStartDay: Boolean,
    val isEndDay: Boolean,
    val weekRange: IntRange,
    val order: Int
)
