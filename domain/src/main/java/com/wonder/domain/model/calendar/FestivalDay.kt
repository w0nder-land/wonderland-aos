package com.wonder.domain.model.calendar

data class FestivalDay(
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
