package com.wonder.data.remote

import com.wonder.domain.model.CheckServer

internal interface AppRemoteDataSource {

    suspend fun checkServer(): CheckServer
}
