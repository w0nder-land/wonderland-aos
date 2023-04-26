package com.wonder.domain.repository

import com.wonder.domain.model.auth.UserAuthInfo
import com.wonder.domain.model.member.UserInfo

interface AuthRepository {

    val userInfo: UserInfo?

    val isSignIn: Boolean

    suspend fun signInKakao(token: String): UserAuthInfo
}
