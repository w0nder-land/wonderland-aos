package com.wonder.data.model.auth

import com.wonder.data.model.mapper.DataToDomainMapper
import com.wonder.domain.model.auth.UserAuthInfo

internal data class UserAuthInfoResponse(
    val message: String,
    val timestamp: Long,
    val data: UserAuthInfoDataResponse
) : DataToDomainMapper<UserAuthInfo> {
    override fun mapper(): UserAuthInfo = data.mapper()
}

internal data class UserAuthInfoDataResponse(
    val nickname: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val socialType: String,
    val token: String,
    val tokenExpiredAt: String,
    val userId: Int
) : DataToDomainMapper<UserAuthInfo> {
    override fun mapper() = UserAuthInfo(
        nickname = nickname,
        refreshToken = refreshToken,
        refreshTokenExpiredAt = refreshTokenExpiredAt,
        socialType = socialType,
        token = token,
        tokenExpiredAt = tokenExpiredAt,
        userId = userId
    )
}
