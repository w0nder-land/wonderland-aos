package com.wonder.data.remote.auth

import com.wonder.data.model.auth.SignInKakaoRequest
import com.wonder.data.service.AuthService
import com.wonder.domain.model.auth.UserAuthInfo
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {

    override suspend fun signInKakao(token: String): UserAuthInfo =
        authService.signInKakao(
            signInKakaoRequest = SignInKakaoRequest(token)
        ).mapper()
}
