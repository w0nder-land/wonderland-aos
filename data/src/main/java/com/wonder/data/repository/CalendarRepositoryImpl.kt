package com.wonder.data.repository

import com.wonder.data.local.calendar.CalendarLocalDataSource
import com.wonder.domain.model.calendar.CalendarInfo
import com.wonder.domain.repository.CalendarRepository
import javax.inject.Inject

internal class CalendarRepositoryImpl @Inject constructor(
    private val calendarLocalDataSource: CalendarLocalDataSource
) : CalendarRepository {

    override suspend fun saveCalendarInfo(
        yearMonth: String,
        calendarInfo: CalendarInfo
    ) {
        calendarLocalDataSource.saveCalendarInfo(
            yearMonth = yearMonth,
            calendarInfo = calendarInfo
        )
    }

    override suspend fun getCalendarInfo(yearMonth: String): CalendarInfo =
        calendarLocalDataSource.getCalendarInfo(yearMonth)
}
