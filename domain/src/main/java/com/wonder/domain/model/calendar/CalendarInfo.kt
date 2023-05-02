package com.wonder.domain.model.calendar

data class CalendarInfo(
    val year: Int = 2023,
    val month: Int = 1,
    val today: Int = 1,
    val calendarDays: List<CalendarDayInfo> = emptyList()
)

data class CalendarDayInfo(
    val year: Int,
    val month: Int,
    val day: Int,
    val festivalDays: List<FestivalDay> = emptyList()
)
