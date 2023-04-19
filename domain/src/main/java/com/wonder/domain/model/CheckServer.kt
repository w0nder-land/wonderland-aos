package com.wonder.domain.model

data class CheckServer(
    val data: CheckServerData,
    val message: String,
    val timestamp: Int
)

data class CheckServerData(
    val application: String,
    val profiles: List<String>,
    val health: String,
    val time: String
)
