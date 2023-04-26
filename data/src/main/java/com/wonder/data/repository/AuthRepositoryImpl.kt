package com.wonder.data.repository

import com.wonder.data.remote.auth.AuthRemoteDataSource
import com.wonder.domain.model.auth.UserAuthInfo
import com.wonder.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun signInKakao(token: String): UserAuthInfo =
        authRemoteDataSource.signInKakao(token)
}
