package com.wonder.data.model

data class SingleResponse<T : DataResponse>(
    val message: String,
    val timestamp: Long,
    val data: T
)

internal data class ListResponse<T : DataResponse>(
    val message: String,
    val timestamp: Long,
    val data: List<T>?
)

interface DataResponse
