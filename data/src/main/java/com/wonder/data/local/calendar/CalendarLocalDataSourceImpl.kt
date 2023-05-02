package com.wonder.data.local.calendar

import com.google.gson.Gson
import com.wonder.data.db.dao.CalendarDao
import com.wonder.data.model.calendar.CalendarInfoEntity
import com.wonder.domain.model.calendar.CalendarInfo
import javax.inject.Inject

internal class CalendarLocalDataSourceImpl @Inject constructor(
    private val calendarDao: CalendarDao
) : CalendarLocalDataSource {

    override suspend fun saveCalendarInfo(
        yearMonth: String,
        calendarInfo: CalendarInfo
    ) {
        val calendarInfoEntity = CalendarInfoEntity(
            yearMonth = yearMonth,
            year = calendarInfo.year,
            month = calendarInfo.month,
            calendarDays = Gson().toJson(calendarInfo.calendarDays)
        )
        calendarDao.saveCalendarInfo(calendarInfoEntity)
    }

    override suspend fun getCalendarInfo(yearMonth: String): CalendarInfo =
        calendarDao.getCalendarInfo(yearMonth).mapper()
}
