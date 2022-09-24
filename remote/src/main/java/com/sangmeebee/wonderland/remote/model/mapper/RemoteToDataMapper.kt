package com.sangmeebee.wonderland.remote.model.mapper

internal interface RemoteToDataMapper<T> {
    fun toData(): T
}

internal fun <T> List<RemoteToDataMapper<T>>.toData(): List<T> = map { it.toData() }