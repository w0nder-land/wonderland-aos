package com.wonder.domain.usecase.auth

import com.wonder.domain.model.auth.UserAuthInfo
import com.wonder.domain.repository.AuthRepository
import com.wonder.domain.usecase.UseCase
import javax.inject.Inject

class SignInKakaoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : UseCase<Long, UserAuthInfo>() {

    override suspend fun execute(parameters: Long): UserAuthInfo =
        authRepository.signInKakao(parameters)
}
