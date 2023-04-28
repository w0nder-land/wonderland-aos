package com.wonder.domain.usecase.festival

import com.wonder.domain.model.festival.Festival
import com.wonder.domain.repository.FestivalRepository
import com.wonder.domain.usecase.UseCase
import javax.inject.Inject

class SearchFestivalsUseCase @Inject constructor(
    private val festivalRepository: FestivalRepository
) : UseCase<SearchFestivalParam, Festival>() {

    override suspend fun execute(parameters: SearchFestivalParam): Festival =
        festivalRepository.searchFestivals(
            date = parameters.date,
            likeStatus = parameters.likeStatus,
            category = parameters.category,
            state = parameters.state,
            region = parameters.region,
            age = parameters.age,
            page = parameters.page,
            size = parameters.size
        )
}

data class SearchFestivalParam(
    val date: String,
    val likeStatus: Boolean = false,
    val category: String? = null,
    val state: String? = null,
    val region: String? = null,
    val age: String? = null,
    val page: Int? = null,
    val size: Int? = 150,
)
