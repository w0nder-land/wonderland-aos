package com.wonder.data.local.calendar

import com.wonder.domain.model.calendar.CalendarInfo

internal interface CalendarLocalDataSource {

    suspend fun saveCalendarInfo(
        yearMonth: String,
        calendarInfo: CalendarInfo
    )

    suspend fun getCalendarInfo(yearMonth: String): CalendarInfo
}
