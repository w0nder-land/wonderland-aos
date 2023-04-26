package com.wonder.domain.repository

import com.wonder.domain.model.auth.UserAuthInfo

interface AuthRepository {

    suspend fun signInKakao(token: String): UserAuthInfo
}
