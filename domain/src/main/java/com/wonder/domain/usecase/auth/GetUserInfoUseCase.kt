package com.wonder.domain.usecase.auth

import com.wonder.domain.model.member.UserInfo
import com.wonder.domain.repository.AuthRepository
import com.wonder.domain.usecase.NoParamUseCase
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : NoParamUseCase<UserInfo?>() {

    override suspend fun execute(): UserInfo? = authRepository.userInfo
}
