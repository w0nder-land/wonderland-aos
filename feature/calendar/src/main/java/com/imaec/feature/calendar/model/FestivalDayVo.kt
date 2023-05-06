package com.imaec.feature.calendar.model

import com.wonder.domain.model.calendar.FestivalDay

internal data class FestivalDayVo(
    val festivalId: Int,
    val festivalName: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val isStartDay: Boolean,
    val isEndDay: Boolean,
    val weekRange: IntRange,
    val festivalCountInWeek: Int,
    val order: Int
)

internal fun FestivalDay.toVo() = FestivalDayVo(
    festivalId = festivalId,
    festivalName = festivalName,
    year = year,
    month = month,
    day = day,
    isStartDay = isStartDay,
    isEndDay = isEndDay,
    weekRange = weekRange,
    festivalCountInWeek = festivalCountInWeek,
    order = order
)

internal fun FestivalDayVo.toDomain() = FestivalDay(
    festivalId = festivalId,
    festivalName = festivalName,
    year = year,
    month = month,
    day = day,
    isStartDay = isStartDay,
    isEndDay = isEndDay,
    weekRange = weekRange,
    festivalCountInWeek = festivalCountInWeek,
    order = order
)
