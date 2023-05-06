package com.imaec.feature.calendar.model

import com.wonder.domain.model.calendar.CalendarDayInfo
import com.wonder.domain.model.calendar.CalendarInfo

internal data class CalendarInfoVo(
    val year: Int = 2023,
    val month: Int = 1,
    val today: Int = 1,
    val calendarDays: List<CalendarDayInfoVo> = emptyList()
)

internal data class CalendarDayInfoVo(
    val year: Int,
    val month: Int,
    val day: Int,
    val festivalDays: List<FestivalDayVo> = emptyList()
)

internal fun CalendarInfo.toVo() = CalendarInfoVo(
    year = year,
    month = month,
    calendarDays = calendarDays.map { it.toVo() }
)

internal fun CalendarInfoVo.toDomain() = CalendarInfo(
    year = year,
    month = month,
    calendarDays = calendarDays.map { it.toDomain() }
)

internal fun CalendarDayInfo.toVo() = CalendarDayInfoVo(
    year = year,
    month = month,
    day = day,
    festivalDays = festivalDays.map { it.toVo() }
)

internal fun CalendarDayInfoVo.toDomain() = CalendarDayInfo(
    year = year,
    month = month,
    day = day,
    festivalDays = festivalDays.map { it.toDomain() }
)
