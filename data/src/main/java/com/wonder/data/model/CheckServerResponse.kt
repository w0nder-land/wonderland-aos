package com.wonder.data.model

import com.wonder.data.model.mapper.DataToDomainMapper
import com.wonder.domain.model.CheckServer
import com.wonder.domain.model.CheckServerData

internal data class CheckServerResponse(
    val data: CheckServerDataResponse,
    val message: String,
    val timestamp: Int
) : DataToDomainMapper<CheckServer> {
    override fun mapper() = CheckServer(
        data = data.mapper(),
        message = message,
        timestamp = timestamp
    )
}

internal data class CheckServerDataResponse(
    val application: String,
    val profiles: List<String>,
    val health: String,
    val time: String
) : DataToDomainMapper<CheckServerData> {
    override fun mapper() = CheckServerData(
        application = application,
        profiles = profiles,
        health = health,
        time = time
    )
}
