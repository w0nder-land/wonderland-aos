package com.wonder.data.service

import com.wonder.data.model.CheckServerResponse
import retrofit2.http.GET

internal interface AppService {

    @GET("api/v1/health")
    suspend fun checkServer(): CheckServerResponse
}
