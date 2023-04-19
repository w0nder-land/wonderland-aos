package com.wonder.domain.usecase

import com.wonder.domain.model.CheckServer
import com.wonder.domain.repository.AppRepository
import javax.inject.Inject

class CheckServerUseCase @Inject constructor(
    private val appRepository: AppRepository
) : NoParamUseCase<CheckServer>() {

    override suspend fun execute(): CheckServer = appRepository.checkServer()
}
