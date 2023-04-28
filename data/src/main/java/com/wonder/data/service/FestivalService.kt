package com.wonder.data.service

import com.wonder.data.model.SingleResponse
import com.wonder.data.model.festival.FestivalDetailResponse
import com.wonder.data.model.festival.FestivalResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FestivalService {

    /**
     * 축제 탐색
     * @param date yyyy-MM : 월별, yyyy-MM-dd : 일별
     */
    @GET("api/v1/search/festivals")
    suspend fun searchFestivals(
        @Query("date") date: String,
        @Query("likeStatus") likeStatus: Boolean,
        @Query("category") category: String?,
        @Query("state") state: String?,
        @Query("region") region: String?,
        @Query("age") age: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
    ): SingleResponse<FestivalResponse>

    /**
     * 축제 상세
     */
    @GET("api/v1/festival/{festivalId}")
    suspend fun getFestival(
        @Path("festivalId") festivalId: Int
    ): SingleResponse<FestivalDetailResponse>
}
