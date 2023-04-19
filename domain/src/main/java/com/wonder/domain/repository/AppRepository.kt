package com.wonder.domain.repository

import com.wonder.domain.model.CheckServer

interface AppRepository {

    suspend fun checkServer(): CheckServer
}
