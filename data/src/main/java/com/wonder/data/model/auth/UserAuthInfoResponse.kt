package com.wonder.data.model.auth

import com.wonder.data.model.DataResponse
import com.wonder.data.model.mapper.DataToDomainMapper
import com.wonder.domain.model.auth.UserAuthInfo

internal data class UserAuthInfoResponse(
    val userId: Int,
    val nickname: String,
    val socialType: String,
    val token: String,
    val tokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String
) : DataResponse, DataToDomainMapper<UserAuthInfo> {
    override fun mapper() = UserAuthInfo(
        userId = userId,
        nickname = nickname,
        socialType = socialType,
        token = token,
        tokenExpiredAt = tokenExpiredAt,
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt
    )
}
