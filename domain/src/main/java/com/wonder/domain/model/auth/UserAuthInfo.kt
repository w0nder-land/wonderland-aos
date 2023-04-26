package com.wonder.domain.model.auth

data class UserAuthInfo(
    val userId: Int,
    val nickname: String,
    val socialType: String,
    val token: String,
    val tokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
)
