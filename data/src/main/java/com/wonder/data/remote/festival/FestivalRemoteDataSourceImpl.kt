package com.wonder.data.remote.festival

import com.wonder.data.service.FestivalService
import com.wonder.domain.model.festival.Festival
import com.wonder.domain.model.festival.FestivalDetail
import javax.inject.Inject

internal class FestivalRemoteDataSourceImpl @Inject constructor(
    private val festivalService: FestivalService
) : FestivalRemoteDataSource {

    override suspend fun searchFestivals(
        date: String,
        likeStatus: Boolean,
        category: List<String?>,
        state: List<String?>,
        region: List<String?>,
        age: List<String?>,
        page: Int?,
        size: Int?
    ): Festival = festivalService.searchFestivals(
        date = date,
        likeStatus = likeStatus,
        category = category,
        state = state,
        region = region,
        age = age,
        page = page,
        size = size
    ).data.mapper()

    override suspend fun getFestival(festivalId: Int): FestivalDetail =
        festivalService.getFestival(festivalId).data.mapper()
}
