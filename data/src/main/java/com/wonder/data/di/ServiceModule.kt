package com.wonder.data.di

import com.wonder.data.service.AppService
import com.wonder.data.service.AuthService
import com.wonder.data.service.FestivalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideAppService(retrofit: Retrofit): AppService = retrofit.create()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create()

    @Provides
    @Singleton
    fun provideFestivalService(retrofit: Retrofit): FestivalService = retrofit.create()
}
