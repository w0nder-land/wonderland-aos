package com.wonder.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wonder.data.model.calendar.CalendarInfoEntity

@Dao
internal interface CalendarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCalendarInfo(calendarInfoEntity: CalendarInfoEntity)

    @Query("SELECT * FROM CalendarInfoEntity WHERE yearMonth=:yearMonth")
    fun getCalendarInfo(yearMonth: String): CalendarInfoEntity
}
