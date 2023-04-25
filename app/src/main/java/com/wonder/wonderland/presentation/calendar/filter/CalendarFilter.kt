package com.wonder.wonderland.presentation.calendar.filter

data class CalendarFilter(
    val title: String,
    val count: Int,
    val isSelected: Boolean = false
)
