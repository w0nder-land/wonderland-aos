package com.wonder.data.service

import com.wonder.data.model.auth.UserAuthInfoResponse
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AuthService {

    @POST("api/v1/signin/kakao")
    suspend fun signInKakao(
        @Query("uid") uid: Int
    ): UserAuthInfoResponse
}
