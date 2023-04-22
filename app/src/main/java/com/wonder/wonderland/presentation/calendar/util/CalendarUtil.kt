package com.wonder.wonderland.presentation.calendar.util

import com.imaec.model.FestivalInfo
import com.wonder.component.util.getCurrentMonth
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
    val calendar = Calendar.getInstance()
    val month = calendar[Calendar.MONTH]
    val today = calendar[Calendar.DAY_OF_MONTH]
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDayOfWeek = calendar[Calendar.DAY_OF_WEEK]
    val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    // 전 월
    calendar.set(Calendar.MONTH, calendar[Calendar.MONTH])
    val beforeMonthLastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val sortedFestivals = festivals.sortedBy { it.startDate }

    val calendarDays = getCalendarDays(
        firstDay = 1,
        lastDay = lastDayOfMonth + 1,
        currentMonth = month,
        festivals = sortedFestivals
    )

    val beforeCalendarDays = getCalendarDays(
        firstDay = beforeMonthLastDayOfMonth - firstDayOfWeek + 1,
        lastDay = beforeMonthLastDayOfMonth,
        currentMonth = month - 1,
        festivals = sortedFestivals
    )

    val afterCalendarDays = getCalendarDays(
        firstDay = 1,
        lastDay = 7 - (firstDayOfWeek - 1 + lastDayOfMonth) % 7 + 1,
        currentMonth = month + 1,
        festivals = sortedFestivals
    )

    return CalendarInfo(
        currentMonth = getCurrentMonth(),
        today = today,
        firstDayOfWeek = firstDayOfWeek,
        lastDayOfMonth = lastDayOfMonth,
        calendarDays = calendarDays,
        beforeCalendarDays = beforeCalendarDays,
        afterCalendarDays = afterCalendarDays,
        beforeMonthLastDayOfMonth = beforeMonthLastDayOfMonth,
    )
}

private suspend fun getCalendarDays(
    firstDay: Int,
    lastDay: Int,
    currentMonth: Int,
    festivals: List<FestivalInfo>,
): List<CalendarDayInfo> = withContext(Dispatchers.Default) {
    val calendarDays = mutableListOf<CalendarDayInfo>()
    for (day in firstDay until lastDay) {
        val festivalDays = mutableListOf<FestivalDay>()
        festivals.forEachIndexed { festivalIndex, festivalInfo ->
            val startDate = festivalInfo.startDate.toDate("yyyy.MM.dd")
            val startDay = startDate.toDay()
            val endDate = festivalInfo.endDate.toDate("yyyy.MM.dd")
            val endDay = endDate.toDay()

            for (festivalDay in startDay until endDay + 1) {
                if (festivalDay == day) {
                    if (Calendar.getInstance().apply {
                            time = startDate
                            add(Calendar.DAY_OF_MONTH, festivalDay - startDay)
                        }[Calendar.MONTH] != currentMonth
                    ) return@forEachIndexed

                    // 해당일이 해당하는 주에 다른 축제가 먼저 있었으면
                    // order += 1

                    // 해당일의 주, 시작일(일요일)과 마지막일(토요일)을 가져온다
                    val currentWeekCalendar = Calendar.getInstance().apply {
                        time = startDate
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

                    // 해당일이 해당하는 주에 다른 축제 수
                    val festivalDayCalendar = startDate.toCalendar().apply {
                        add(Calendar.DAY_OF_YEAR, festivalDay - startDay)
                    }
                    val festivalDayOfYear = festivalDayCalendar[Calendar.DAY_OF_YEAR]
                    var order = 0
                    festivals.take(festivalIndex).forEach {
                        if (festivalDayOfYear < weekRange.first || festivalDayOfYear > weekRange.last) return@forEach

                        val beforeFestivalStartCalendar = it.startDate.toDate("yyyy.MM.dd").toCalendar()
                        val beforeFestivalStartDay = beforeFestivalStartCalendar[Calendar.DAY_OF_YEAR]
                        val beforeFestivalEndCalendar = it.endDate.toDate("yyyy.MM.dd").toCalendar()
                        val beforeFestivalEndDay = beforeFestivalEndCalendar[Calendar.DAY_OF_YEAR]

                        if (weekRange.contains(beforeFestivalStartDay) ||
                            weekRange.contains(beforeFestivalEndDay)
                        ) {
                            order++
                        } else if (beforeFestivalStartDay < weekRange.first &&
                            beforeFestivalEndDay > weekRange.last
                        ) {
                            order++
                        }
                    }

                    festivalDays.add(
                        FestivalDay(
                            festivalName = festivalInfo.name,
                            day = festivalDay,
                            startDay = startDay,
                            endDay = endDay,
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
