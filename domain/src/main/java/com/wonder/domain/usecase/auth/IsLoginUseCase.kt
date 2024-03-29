package com.wonder.domain.usecase.auth

import com.wonder.domain.repository.AuthRepository
import com.wonder.domain.usecase.ResultNoParamUseCase
import javax.inject.Inject

class IsLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : ResultNoParamUseCase<Boolean>() {

    override suspend fun execute(): Boolean = authRepository.isSignIn
}
