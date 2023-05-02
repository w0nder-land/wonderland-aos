package com.wonder.data.di

import android.content.Context
import com.wonder.data.db.AppDatabase
import com.wonder.data.db.dao.CalendarDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideCalendarDao(appDatabase: AppDatabase): CalendarDao =
        appDatabase.calendarDao()
}
