package com.wonder.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wonder.data.db.dao.CalendarDao
import com.wonder.data.model.calendar.CalendarInfoEntity

@Database(
    entities = [
        CalendarInfoEntity::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun calendarDao(): CalendarDao

    companion object {
        private const val DB_NAME = "wonderland-db"

        fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }
}
