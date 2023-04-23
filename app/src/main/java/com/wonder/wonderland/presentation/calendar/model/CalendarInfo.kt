package com.wonder.wonderland.presentation.calendar.model

internal data class CalendarInfo(
    val currentYearMonth: String = "",
    val today: Int = 1,
    val firstDayOfWeek: Int = 1,
    val lastDayOfMonth: Int = 30,
    val calendarDays: List<CalendarDayInfo> = emptyList(),
    val beforeCalendarDays: List<CalendarDayInfo> = emptyList(),
    val afterCalendarDays: List<CalendarDayInfo> = emptyList(),
    val beforeMonthDayCount: Int = firstDayOfWeek - 1,
    val afterMonthDayCount: Int = 7 - (beforeMonthDayCount + lastDayOfMonth) % 7
)

internal data class CalendarDayInfo(
    val day: String,
    val festivalDays: List<FestivalDay> = emptyList()
)
