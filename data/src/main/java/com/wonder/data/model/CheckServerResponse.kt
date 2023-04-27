package com.wonder.data.model

import com.wonder.data.model.mapper.DataToDomainMapper
import com.wonder.domain.model.CheckServer

internal data class CheckServerResponse(
    val application: String,
    val profiles: List<String>,
    val health: String,
    val time: String
) : DataResponse, DataToDomainMapper<CheckServer> {
    override fun mapper() = CheckServer(
        application = application,
        profiles = profiles,
        health = health,
        time = time
    )
}
