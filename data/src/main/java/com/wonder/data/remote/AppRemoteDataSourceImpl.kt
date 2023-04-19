package com.wonder.data.remote

import com.wonder.data.service.AppService
import javax.inject.Inject

internal class AppRemoteDataSourceImpl @Inject constructor(
    private val appService: AppService
) : AppRemoteDataSource {

    override suspend fun checkServer() = appService.checkServer().mapper()
}
