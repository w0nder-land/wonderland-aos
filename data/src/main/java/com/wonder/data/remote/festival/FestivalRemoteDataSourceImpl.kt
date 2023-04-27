package com.wonder.data.remote.festival

import com.wonder.data.service.FestivalService
import com.wonder.domain.model.Festival
import javax.inject.Inject

internal class FestivalRemoteDataSourceImpl @Inject constructor(
    private val festivalService: FestivalService
) : FestivalRemoteDataSource {

    override suspend fun searchFestivals(
        date: String,
        likeStatus: Boolean,
        category: String?,
        state: String?,
        region: String?,
        age: String?,
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
}
