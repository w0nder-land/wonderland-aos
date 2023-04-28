package com.wonder.wonderland.presentation.calendar.model

internal data class CalendarInfo(
    val year: Int = 2023,
    val month: Int = 1,
    val today: Int = 1,
    val firstDayOfWeek: Int = 1,
    val lastDayOfMonth: Int = 30,
    val calendarDays: List<CalendarDayInfo> = emptyList(),
    val beforeCalendarDays: List<CalendarDayInfo> = emptyList(),
    val afterCalendarDays: List<CalendarDayInfo> = emptyList(),
    val beforeMonthDayCount: Int = if (firstDayOfWeek - 1 == 0) 7 else firstDayOfWeek - 1,
    val afterMonthDayCount: Int = 7 - (beforeMonthDayCount + lastDayOfMonth) % 7
)

internal data class CalendarDayInfo(
    val day: String,
    val festivalDays: List<FestivalDay> = emptyList()
)
