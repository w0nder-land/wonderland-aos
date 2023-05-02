package com.wonder.domain.repository

import com.wonder.domain.model.calendar.CalendarInfo

interface CalendarRepository {

    suspend fun saveCalendarInfo(
        yearMonth: String,
        calendarInfo: CalendarInfo
    )

    suspend fun getCalendarInfo(yearMonth: String): CalendarInfo
}
