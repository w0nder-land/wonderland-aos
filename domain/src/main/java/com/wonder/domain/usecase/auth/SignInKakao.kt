package com.wonder.domain.usecase.auth

import com.wonder.domain.model.auth.UserAuthInfo
import com.wonder.domain.repository.AuthRepository
import com.wonder.domain.usecase.UseCase
import javax.inject.Inject

class SignInKakao @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<Int, UserAuthInfo>() {

    override suspend fun execute(parameters: Int): UserAuthInfo =
        authRepository.signInKakao(parameters)
}
