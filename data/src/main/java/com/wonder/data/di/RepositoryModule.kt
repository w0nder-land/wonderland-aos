package com.wonder.data.di

import com.wonder.data.repository.AppRepositoryImpl
import com.wonder.domain.repository.AppRepository
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
}