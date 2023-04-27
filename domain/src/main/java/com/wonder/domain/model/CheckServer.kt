package com.wonder.domain.model

data class CheckServer(
    val application: String,
    val profiles: List<String>,
    val health: String,
    val time: String
)
