package com.imaec.model.festival

import com.wonder.domain.model.FestivalItem

data class FestivalItemVo(
    val festivalId: Int,
    val festivalName: String,
    val thumbnailUrl: String?,
    val startDate: String,
    val endDate: String
)

fun FestivalItem.toVo() = FestivalItemVo(
    festivalId = festivalId,
    festivalName = festivalName,
    thumbnailUrl = thumbnailUrl,
    startDate = startDate,
    endDate = endDate
)
