package com.wonder.data.model.calendar

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.wonder.data.model.mapper.DataToDomainMapper
import com.wonder.domain.model.calendar.CalendarDayInfo
import com.wonder.domain.model.calendar.CalendarInfo

@Entity
internal data class CalendarInfoEntity(
    @PrimaryKey val yearMonth: String,
    val year: Int,
    val month: Int,
    val calendarDays: String,
) : DataToDomainMapper<CalendarInfo> {
    override fun mapper() = CalendarInfo(
        year = year,
        month = month,
        calendarDays = Gson().fromJson(
            calendarDays,
            Array<CalendarDayInfo>::class.java
        ).toList()
    )
}
