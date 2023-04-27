package com.wonder.wonderland.presentation.calendar.model

internal data class FestivalDay(
    val festivalId: Int,
    val festivalName: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val isStartDay: Boolean,
    val isEndDay: Boolean,
    val weekRange: IntRange,
    val festivalCountInWeek: Int,
    val order: Int
)
