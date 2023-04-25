package com.wonder.domain.model.auth

data class UserAuthInfo(
    val nickname: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val socialType: String,
    val token: String,
    val tokenExpiredAt: String,
    val userId: Int
)
