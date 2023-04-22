package com.wonder.wonderland.presentation.calendar.model

internal data class CalendarInfo(
    val currentMonth: String = "",
    val today: Int = 1,
    val firstDayOfWeek: Int = 1,
    val lastDayOfMonth: Int = 0,
    val beforeMonthLastDayOfMonth: Int = 0,
    val calendarDays: List<CalendarDayInfo> = List(
        size = lastDayOfMonth,
        init = {
            CalendarDayInfo(
                day = "${it + 1}"
            )
        }
    ),
    val beforeCalendarDays: List<CalendarDayInfo> = List(
        size = lastDayOfMonth,
        init = {
            CalendarDayInfo(
                day = "${it + 1}"
            )
        }
    ),
    val afterCalendarDays: List<CalendarDayInfo> = List(
        size = lastDayOfMonth,
        init = {
            CalendarDayInfo(
                day = "${it + 1}"
            )
        }
    ),
    val beforeMonthDayCount: Int = firstDayOfWeek - 1,
    val afterMonthDayCount: Int = 7 - (beforeMonthDayCount + lastDayOfMonth) % 7,
    val currentCalendarDayCount: Int = beforeMonthDayCount + lastDayOfMonth + afterMonthDayCount
)

internal data class CalendarDayInfo(
    val day: String,
    val festivalDays: List<FestivalDay> = emptyList()
)
