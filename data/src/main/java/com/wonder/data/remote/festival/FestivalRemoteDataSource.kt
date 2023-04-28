package com.wonder.data.remote.festival

import com.wonder.domain.model.festival.Festival
import com.wonder.domain.model.festival.FestivalDetail

internal interface FestivalRemoteDataSource {

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
