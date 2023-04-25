package com.wonder.data.remote.auth

import com.wonder.domain.model.auth.UserAuthInfo

internal interface AuthRemoteDataSource {

    suspend fun signInKakao(uid: Int): UserAuthInfo
}
