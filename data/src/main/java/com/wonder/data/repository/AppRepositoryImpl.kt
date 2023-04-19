package com.wonder.data.repository

import com.wonder.data.remote.AppRemoteDataSource
import com.wonder.domain.repository.AppRepository
import javax.inject.Inject

internal class AppRepositoryImpl @Inject constructor(
    private val appRemoteDataSource: AppRemoteDataSource
) : AppRepository {

    override suspend fun checkServer() = appRemoteDataSource.checkServer()
}
