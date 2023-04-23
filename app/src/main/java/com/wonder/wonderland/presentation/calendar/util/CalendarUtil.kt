package com.wonder.wonderland.presentation.calendar.util

import com.imaec.model.FestivalInfo
import com.wonder.component.util.getCurrentYearMonth
import com.wonder.component.util.toCalendar
import com.wonder.component.util.toDate
import com.wonder.component.util.toDay
import com.wonder.wonderland.presentation.calendar.model.CalendarDayInfo
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo
import com.wonder.wonderland.presentation.calendar.model.FestivalDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

internal suspend fun getCalendarInfo(
    festivals: List<FestivalInfo>
): CalendarInfo {
    // 현재 월
    // month : 현재 월
    // firstDayOfWeek : 현재 월의 첫 번째 요일
    // lastDayOfMonth : 현재 월의 마지막 날
    val calendar = Calendar.getInstance()
    val month = calendar[Calendar.MONTH]
    val today = calendar[Calendar.DAY_OF_MONTH]
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDayOfWeek = calendar[Calendar.DAY_OF_WEEK]
    val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    // 전 월
    // beforeMonthLastDayOfMonth : 전 월의 마자믹 날
    calendar.set(Calendar.MONTH, calendar[Calendar.MONTH])
    val beforeMonthLastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val sortedFestivals = festivals.sortedBy { it.startDate }

    // 현재 월의 달력 정보를 가져온다
    val calendarDays = getCalendarDays(
        firstDay = 1,
        lastDay = lastDayOfMonth + 1,
        currentMonth = month,
        festivals = sortedFestivals
    )

    // 현재 월 달력에서 전 월로 표시되는 달력 정보를 가져온다
    val beforeCalendarDays = getCalendarDays(
        firstDay = beforeMonthLastDayOfMonth - firstDayOfWeek + 1,
        lastDay = beforeMonthLastDayOfMonth,
        currentMonth = month - 1,
        festivals = sortedFestivals
    )

    // 현재 월 달력에서 다음 월로 표시되는 달력 정보를 가져온다
    val afterCalendarDays = getCalendarDays(
        firstDay = 1,
        lastDay = 7 - (firstDayOfWeek - 1 + lastDayOfMonth) % 7 + 1,
        currentMonth = month + 1,
        festivals = sortedFestivals
    )

    return CalendarInfo(
        currentYearMonth = getCurrentYearMonth(),
        today = today,
        firstDayOfWeek = firstDayOfWeek,
        lastDayOfMonth = lastDayOfMonth,
        calendarDays = calendarDays,
        beforeCalendarDays = beforeCalendarDays,
        afterCalendarDays = afterCalendarDays
    )
}

/**
 * 달력 정보
 * @param firstDay 달력의 첫번째 날짜
 * @param lastDay 달력의 마지막 날짜
 * @param currentMonth 현재 월
 * @param festivals 축제 정보
 */
private suspend fun getCalendarDays(
    firstDay: Int,
    lastDay: Int,
    currentMonth: Int,
    festivals: List<FestivalInfo>,
): List<CalendarDayInfo> = withContext(Dispatchers.Default) {
    // 달력 정보, 현재 일자와 축제 일정을 가진다
    val calendarDays = mutableListOf<CalendarDayInfo>()

    for (day in firstDay until lastDay) {
        // 현재 일자에 들어갈 축제 일정
        val festivalDays = mutableListOf<FestivalDay>()

        festivals.forEachIndexed { festivalIndex, festivalInfo ->
            // startDay : 축제 시작 일자
            // endDay : 축제 종료 일자
            val startDate = festivalInfo.startDate.toDate("yyyy.MM.dd")
            val startDay = startDate.toDay()
            val endDate = festivalInfo.endDate.toDate("yyyy.MM.dd")
            val endDay = endDate.toDay()

            // 해당 축제일에 다른 축제가 있으면 달력에서 일정이 쌓일 수 있도록 order를 증가
            for (festivalDay in startDay until endDay + 1) {
                if (festivalDay == day) {
                    // 축제 일이 현재 월인 경우에만 달력에 축제 일정을 추가
                    if (Calendar.getInstance().apply {
                        time = startDate
                        add(Calendar.DAY_OF_MONTH, festivalDay - startDay)
                    }[Calendar.MONTH] != currentMonth
                    ) return@forEachIndexed

                    // 해당 일의 주의 시작일(일요일)과 마지막 일(토요일)을 가져온다
                    val currentWeekCalendar = startDate.toCalendar().apply {
                        add(Calendar.DAY_OF_MONTH, festivalDay - startDay)
                        set(Calendar.DAY_OF_MONTH, festivalDay)
                        if (get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                        }
                    }
                    val weekRange = IntRange(
                        currentWeekCalendar[Calendar.DAY_OF_YEAR],
                        currentWeekCalendar[Calendar.DAY_OF_YEAR] + 6
                    )

                    // 축제 일정의 시작일과 종료일을 가져온다
                    val festivalDayCalendar = startDate.toCalendar()
                    val festivalDayRange = IntRange(
                        festivalDayCalendar[Calendar.DAY_OF_YEAR],
                        festivalDayCalendar[Calendar.DAY_OF_YEAR] + (endDay - startDay)
                    )
                    // 현재 축제 일
                    festivalDayCalendar.add(Calendar.DAY_OF_YEAR, festivalDay - startDay)
                    val festivalDayOfYear = festivalDayCalendar[Calendar.DAY_OF_YEAR]

                    // 해당 일이 속한 주의 다른 축제 수
                    var order = 0
                    festivals.take(festivalIndex).forEach {
                        // 축제 시작일이 현재 주의 시작일보다 작거나 축제 종료일이 현재 주의 마지막 날보다 크면
                        // 해당 일의 첫 번째 축제이기 때문에 order를 증가시키지 않는다
                        if (festivalDayOfYear < weekRange.first || festivalDayOfYear > weekRange.last) return@forEach

                        // beforeFestivalStartDay : 이전 축제의 시작일
                        // beforeFestivalEndDay : 이전 축제의 종료일
                        val beforeFestivalStartCalendar = it.startDate.toDate("yyyy.MM.dd").toCalendar()
                        val beforeFestivalStartDay = beforeFestivalStartCalendar[Calendar.DAY_OF_YEAR]
                        val beforeFestivalEndCalendar = it.endDate.toDate("yyyy.MM.dd").toCalendar()
                        val beforeFestivalEndDay = beforeFestivalEndCalendar[Calendar.DAY_OF_YEAR]

                        if (weekRange.contains(beforeFestivalStartDay) ||
                            weekRange.contains(beforeFestivalEndDay)
                        ) { // 이전 축제의 시작일이나 종료일이 현재 주에 속해 있을 경우
                            if (festivalDayRange.contains(beforeFestivalStartDay) ||
                                festivalDayRange.contains(beforeFestivalEndDay)
                            ) { // 이전 축제가 현재 축제 기간에 속해 있을 경우
                                order = if (festivalDay == startDay) { // 축제 시작 일인 경우
                                    (festivalDays.lastOrNull()?.order ?: 0) + 1
                                } else { // 축제 시작일이 아닌 경우
                                    val beforeDay = calendarDays.lastOrNull()
                                    val beforeFestivalDay = beforeDay
                                        ?.festivalDays
                                        ?.firstOrNull { beforeFestivalDay ->
                                            beforeFestivalDay.festivalName == festivalInfo.name
                                        }
                                    if (beforeFestivalDay?.weekRange?.first == weekRange.first) { // 축제 시작일의 주와 같은 주일 경우
                                        beforeFestivalDay.order
                                    } else { // 축제 시작일의 주와 같은 주가 아닐 경우
                                        festivalDays.size
                                    }
                                }
                            }
                        } else if (beforeFestivalStartDay < weekRange.first &&
                            beforeFestivalEndDay > weekRange.last
                        ) { // 이전 축제 기간이 현재 주보다 클 경우
                            order = if (festivalDay == startDay) { // 축제 시작 일인 경우
                                (festivalDays.lastOrNull()?.order ?: 0) + 1
                            } else { // 축제 시작일이 아닌 경우
                                calendarDays
                                    .lastOrNull()
                                    ?.festivalDays
                                    ?.firstOrNull { beforeFestivalDay ->
                                        beforeFestivalDay.festivalName == festivalInfo.name
                                    }?.order ?: 0
                            }
                        }
                    }

                    festivalDays.add(
                        FestivalDay(
                            festivalName = festivalInfo.name,
                            day = festivalDay,
                            startDay = startDay,
                            endDay = endDay,
                            weekRange = weekRange,
                            order = order
                        )
                    )
                }
            }
        }

        calendarDays.add(
            CalendarDayInfo(
                day = "$day",
                festivalDays = festivalDays
            )
        )
    }
    calendarDays
}
