package com.wonder.wonderland.presentation.calendar.model

internal data class CalendarInfo(
    val currentMonth: String = "",
    val today: Int = 1,
    val firstDayOfWeek: Int = 1,
    val lastDayOfMonth: Int = 0,
    val beforeMonthLastDayOfMonth: Int = 0,
    val beforeMonthDayCount: Int = firstDayOfWeek - 1,
    val afterMonthDayCount: Int = 7 - (beforeMonthDayCount + lastDayOfMonth) % 7,
    val currentCalendarDayCount: Int = beforeMonthDayCount + lastDayOfMonth + afterMonthDayCount
)
