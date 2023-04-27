package com.wonder.data.service

import com.wonder.data.model.SingleResponse
import com.wonder.data.model.auth.SignInKakaoRequest
import com.wonder.data.model.auth.UserAuthInfoResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {

    @POST("api/v1/signin/kakao")
    suspend fun signInKakao(
        @Body signInKakaoRequest: SignInKakaoRequest
    ): SingleResponse<UserAuthInfoResponse>
}
