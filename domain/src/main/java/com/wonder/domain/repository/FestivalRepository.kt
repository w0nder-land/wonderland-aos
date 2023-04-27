package com.wonder.domain.repository

import com.wonder.domain.model.Festival

interface FestivalRepository {

    suspend fun searchFestivals(
        date: String,
        likeStatus: Boolean,
        category: String?,
        state: String?,
        region: String?,
        age: String?,
        page: Int?,
        size: Int?,
    ): Festival
}
