package com.wonder.domain.repository

import com.wonder.domain.model.festival.Festival
import com.wonder.domain.model.festival.FestivalDetail

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

    suspend fun getFestival(festivalId: Int): FestivalDetail
}
