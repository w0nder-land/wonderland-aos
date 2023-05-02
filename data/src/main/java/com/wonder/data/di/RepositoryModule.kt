package com.wonder.data.di

import com.wonder.data.repository.AppRepositoryImpl
import com.wonder.data.repository.AuthRepositoryImpl
import com.wonder.data.repository.CalendarRepositoryImpl
import com.wonder.data.repository.FestivalRepositoryImpl
import com.wonder.domain.repository.AppRepository
import com.wonder.domain.repository.AuthRepository
import com.wonder.domain.repository.CalendarRepository
import com.wonder.domain.repository.FestivalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsFestivalRepository(
        festivalRepositoryImpl: FestivalRepositoryImpl
    ): FestivalRepository

    @Binds
    @Singleton
    abstract fun bindsCalendarRepository(
        calendarRepositoryImpl: CalendarRepositoryImpl
    ): CalendarRepository
}
