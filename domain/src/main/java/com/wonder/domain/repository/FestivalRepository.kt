package com.wonder.domain.repository

import com.wonder.domain.model.festival.Festival
import com.wonder.domain.model.festival.FestivalDetail

interface FestivalRepository {

    suspend fun searchFestivals(
        date: String,
        likeStatus: Boolean,
        category: List<String?>,
        state: List<String?>,
        region: List<String?>,
        age: List<String?>,
        page: Int?,
        size: Int?,
    ): Festival

    suspend fun getFestival(festivalId: Int): FestivalDetail
}
