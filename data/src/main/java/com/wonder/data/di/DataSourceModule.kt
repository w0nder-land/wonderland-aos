package com.wonder.data.di

import com.wonder.data.local.calendar.CalendarLocalDataSource
import com.wonder.data.local.calendar.CalendarLocalDataSourceImpl
import com.wonder.data.remote.AppRemoteDataSource
import com.wonder.data.remote.AppRemoteDataSourceImpl
import com.wonder.data.remote.auth.AuthRemoteDataSource
import com.wonder.data.remote.auth.AuthRemoteDataSourceImpl
import com.wonder.data.remote.festival.FestivalRemoteDataSource
import com.wonder.data.remote.festival.FestivalRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsAppRemoteDataSource(
        appRemoteDataSourceImpl: AppRemoteDataSourceImpl
    ): AppRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsFestivalRemoteDataSource(
        festivalRemoteDataSourceImpl: FestivalRemoteDataSourceImpl
    ): FestivalRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsCalendarLocalDataSource(
        calendarLocalDataSourceImpl: CalendarLocalDataSourceImpl
    ): CalendarLocalDataSource
}
