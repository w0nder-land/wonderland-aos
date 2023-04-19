package com.wonder.data.di

import com.wonder.data.remote.AppRemoteDataSource
import com.wonder.data.remote.AppRemoteDataSourceImpl
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
}
