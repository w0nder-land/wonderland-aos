package com.wonder.domain.usecase.festival

import com.wonder.domain.model.festival.FestivalDetail
import com.wonder.domain.repository.FestivalRepository
import com.wonder.domain.usecase.UseCase
import javax.inject.Inject

class GetFestivalUseCase @Inject constructor(
    private val festivalRepository: FestivalRepository
) : UseCase<Int, FestivalDetail>() {

    override suspend fun execute(parameters: Int): FestivalDetail =
        festivalRepository.getFestival(parameters)
}
