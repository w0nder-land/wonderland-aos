package com.imaec.feature.calendar.filter

data class CalendarFilter(
    val title: String,
    val count: Int,
    val code: String?,
    val isSelected: Boolean = false
)

fun List<CalendarFilter>.isSelected() = filterNot { it.title == "전체" }.any { it.isSelected }
