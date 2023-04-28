package com.wonder.data.repository

import com.wonder.data.remote.festival.FestivalRemoteDataSource
import com.wonder.domain.model.festival.Festival
import com.wonder.domain.model.festival.FestivalDetail
import com.wonder.domain.repository.FestivalRepository
import javax.inject.Inject

internal class FestivalRepositoryImpl @Inject constructor(
    private val festivalRemoteDataSource: FestivalRemoteDataSource
) : FestivalRepository {

    override suspend fun searchFestivals(
        date: String,
        likeStatus: Boolean,
        category: String?,
        state: String?,
        region: String?,
        age: String?,
        page: Int?,
        size: Int?
    ): Festival = festivalRemoteDataSource.searchFestivals(
        date = date,
        likeStatus = likeStatus,
        category = category,
        state = state,
        region = region,
        age = age,
        page = page,
        size = size
    )

    override suspend fun getFestival(festivalId: Int): FestivalDetail =
        festivalRemoteDataSource.getFestival(festivalId)
}
