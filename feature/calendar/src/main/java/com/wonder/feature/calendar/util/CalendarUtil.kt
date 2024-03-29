package com.wonder.feature.calendar.util

import com.wonder.model.festival.FestivalItemVo
import com.wonder.component.util.addDayOfMonth
import com.wonder.component.util.addMonth
import com.wonder.component.util.dayOfMonth
import com.wonder.component.util.dayOfWeek
import com.wonder.component.util.dayOfYear
import com.wonder.component.util.month
import com.wonder.component.util.toCalendar
import com.wonder.component.util.toDate
import com.wonder.component.util.toDay
import com.wonder.component.util.year
import com.wonder.feature.calendar.model.CalendarDayInfoVo
import com.wonder.feature.calendar.model.CalendarInfoVo
import com.wonder.feature.calendar.model.FestivalDayVo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

internal suspend fun getCalendarInfo(
    festivals: List<FestivalItemVo>,
    calendar: Calendar
): CalendarInfoVo {
    // 현재 월
    // month : 현재 월
    // firstDayOfWeek : 현재 월의 첫 번째 요일
    // lastDayOfMonth : 현재 월의 마지막 날
    val year = calendar.year()
    val month = calendar.month()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDayOfWeek = calendar.dayOfWeek()
    val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    // 전 월
    // beforeMonthLastDayOfMonth : 전 월의 마자믹 날
    calendar.addMonth(-1)
    val beforeMonthLastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val sortedFestivals = festivals.sortedBy { it.startDate }.distinctBy { it.festivalName }

    // 현재 월의 달력 정보를 가져온다
    val calendarDays = getCalendarDays(
        firstDay = 1,
        lastDay = lastDayOfMonth + 1,
        currentYear = year,
        currentMonth = month,
        festivals = sortedFestivals
    )

    // 현재 월 달력에서 전 월로 표시되는 달력 정보를 가져온다
    val beforeCalendarDays = getCalendarDays(
        firstDay = (beforeMonthLastDayOfMonth - firstDayOfWeek) + 2,
        lastDay = beforeMonthLastDayOfMonth + 1,
        currentYear = year,
        currentMonth = month - 1,
        festivals = sortedFestivals
    )

    // 현재 월 달력에서 다음 월로 표시되는 달력 정보를 가져온다
    val afterCalendarLastDay = 7 - (firstDayOfWeek - 1 + lastDayOfMonth) % 7
    val afterCalendarDays = if (afterCalendarLastDay < 7) {
        getCalendarDays(
            firstDay = 1,
            lastDay = 7 - (firstDayOfWeek - 1 + lastDayOfMonth) % 7 + 1,
            currentYear = year,
            currentMonth = month + 1,
            festivals = sortedFestivals
        )
    } else {
        emptyList()
    }

    return CalendarInfoVo(
        year = year,
        month = month,
        today = Calendar.getInstance().dayOfMonth(),
        calendarDays = beforeCalendarDays + calendarDays + afterCalendarDays
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
    currentYear: Int,
    currentMonth: Int,
    festivals: List<FestivalItemVo>,
): List<CalendarDayInfoVo> = withContext(Dispatchers.Default) {
    // 달력 정보, 현재 일자와 축제 일정을 가진다
    val calendarDays = mutableListOf<CalendarDayInfoVo>()

    for (day in firstDay until lastDay) {
        // 현재 일자에 들어갈 축제 일정
        val festivalDays = getFestivalDays(
            currentMonth = currentMonth,
            day = day,
            festivals = festivals,
            calendarDays = calendarDays
        )

        calendarDays.add(
            CalendarDayInfoVo(
                year = currentYear,
                month = currentMonth,
                day = day,
                festivalDays = festivalDays
            )
        )
    }
    calendarDays
}

/**
 * 현재 일자에 들어갈 축제 일정
 */
private fun getFestivalDays(
    currentMonth: Int,
    day: Int,
    festivals: List<FestivalItemVo>,
    calendarDays: List<CalendarDayInfoVo>
): List<FestivalDayVo> {
    val festivalDays = mutableListOf<FestivalDayVo>()

    festivals.forEachIndexed { festivalIndex, festivalItem ->
        // startDay : 축제 시작 일자
        // endDay : 축제 종료 일자
        val startDate = festivalItem.startDate.toDate("yyyy-MM-dd")
        val startDay = startDate.toDay()
        val startDayOfYear = startDate.toCalendar().dayOfYear()
        val endDate = festivalItem.endDate.toDate("yyyy-MM-dd")
        val endDay = endDate.toDay()
        val endDayOfYear = endDate.toCalendar().dayOfYear()
        // 축제 시작 월의 마지막 일
        val lastDayOfMonth = startDate.toCalendar().getActualMaximum(Calendar.DAY_OF_MONTH)

        // 해당 축제 일에 다른 축제가 있으면 달력에서 일정이 쌓일 수 있도록 order를 증가
        for (festivalDayOfYear in startDayOfYear until endDayOfYear + 1) {
            val festivalDay = if (festivalDayOfYear - startDayOfYear + startDay <= lastDayOfMonth) {
                festivalDayOfYear - startDayOfYear + startDay
            } else {
                festivalDayOfYear - startDayOfYear + startDay - lastDayOfMonth
            }
            if (festivalDay != day) continue

            // 축제 일이 현재 월인 경우에만 달력에 축제 일정을 추가
            val festivalMonth = startDate
                .toCalendar()
                .addDayOfMonth(festivalDayOfYear - startDayOfYear)
            if (festivalMonth.month() != currentMonth) return@forEachIndexed

            // 해당 일의 주의 시작일(일요일)과 마지막 일(토요일)을 가져온다
            val weekRange = getCurrentWeekRange(
                festivalCalendar = startDate
                    .toCalendar()
                    .addDayOfMonth(festivalDayOfYear - startDayOfYear)
            )

            // 해당 일이 속한 주의 다른 축제 수
            val order = getOrder(
                beforeFestivals = festivals.take(festivalIndex),
                festivalDayOfYear = festivalDayOfYear,
                festivalDay = festivalDay,
                startDay = startDay,
                endDay = endDay,
                weekRange = weekRange,
                festivalCalendar = startDate.toCalendar(),
                festivalItem = festivalItem,
                festivalDays = festivalDays,
                calendarDays = calendarDays
            )

            // 해당 주에 포함된 축제 수
            val festivalCountInWeek = getFestivalCountInWeek(
                festivalCalendar = startDate.toCalendar(),
                startDay = startDayOfYear,
                endDay = endDayOfYear,
                weekRange = weekRange
            )

            festivalDays.add(
                FestivalDayVo(
                    festivalId = festivalItem.festivalId,
                    festivalName = festivalItem.festivalName,
                    year = startDate
                        .toCalendar()
                        .addDayOfMonth(amount = festivalDayOfYear - startDayOfYear)
                        .year(),
                    month = currentMonth,
                    day = festivalDay,
                    isStartDay = festivalDayOfYear == startDayOfYear,
                    isEndDay = festivalDayOfYear == endDayOfYear,
                    weekRange = weekRange,
                    festivalCountInWeek = festivalCountInWeek,
                    order = order
                )
            )
        }
    }

    return festivalDays
}

/**
 * 축제 일이 포함된 주의 범위
 * @param festivalCalendar 축제 일 기준 calendar
 */
private fun getCurrentWeekRange(festivalCalendar: Calendar): IntRange {
    val currentWeekCalendar = festivalCalendar.apply {
        if (dayOfWeek() != Calendar.SUNDAY) {
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        }
    }
    return IntRange(
        currentWeekCalendar.dayOfYear(),
        currentWeekCalendar.dayOfYear() + 6
    )
}

/**
 * 해당 일이 속한 주의 다른 축제 수
 */
private fun getOrder(
    beforeFestivals: List<FestivalItemVo>,
    festivalDayOfYear: Int,
    festivalDay: Int,
    startDay: Int,
    endDay: Int,
    weekRange: IntRange,
    festivalCalendar: Calendar,
    festivalItem: FestivalItemVo,
    festivalDays: List<FestivalDayVo>,
    calendarDays: List<CalendarDayInfoVo>
): Int {
    var order = 0
    // 축제 일정의 시작일과 종료일을 가져온다
    val festivalDayRange = getFestivalRange(
        festivalCalendar = festivalCalendar,
        startDay = startDay,
        endDay = endDay
    )
    beforeFestivals.forEach {
        // 축제 시작일이 현재 주의 시작일보다 작거나 축제 종료일이 현재 주의 마지막 날보다 크면
        // 해당일의 첫 번째 축제이기 때문에 order를 증가시키지 않는다
        if (festivalDayOfYear < weekRange.first || festivalDayOfYear > weekRange.last) {
            return@forEach
        }

        // beforeFestivalStartDay : 이전 축제의 시작일
        // beforeFestivalEndDay : 이전 축제의 종료일
        val beforeFestivalStartDay = it.startDate
            .toDate("yyyy-MM-dd")
            .toCalendar()
            .dayOfYear()
        val beforeFestivalEndDay = it.endDate
            .toDate("yyyy-MM-dd")
            .toCalendar()
            .dayOfYear()

        if (weekRange.contains(beforeFestivalStartDay) ||
            weekRange.contains(beforeFestivalEndDay)
        ) { // 이전 축제의 시작일이나 종료일이 현재 주에 속해 있을 경우
            if (festivalDayRange.contains(beforeFestivalStartDay) ||
                festivalDayRange.contains(beforeFestivalEndDay)
            ) { // 이전 축제가 현재 축제 기간에 속해 있을 경우
                order = getOrder(
                    festivalDay = festivalDay,
                    startDay = startDay,
                    festivalItem = festivalItem,
                    weekRange = weekRange,
                    festivalDays = festivalDays,
                    calendarDays = calendarDays
                )
            } else if (IntRange(
                    beforeFestivalStartDay,
                    beforeFestivalEndDay
                ).contains(festivalDayOfYear)
            ) { // 현재 축제 일이 이전 축제 기간에 포함된 경우
                order = getOrder(
                    festivalDay = festivalDay,
                    startDay = startDay,
                    festivalItem = festivalItem,
                    weekRange = weekRange,
                    festivalDays = festivalDays,
                    calendarDays = calendarDays
                )
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
                        beforeFestivalDay.festivalId == festivalItem.festivalId
                    }?.order ?: 0
            }
        }
    }
    return order
}

/**
 * 해당 일이 속한 주의 다른 축제 수
 */
private fun getOrder(
    festivalDay: Int,
    startDay: Int,
    festivalItem: FestivalItemVo,
    weekRange: IntRange,
    festivalDays: List<FestivalDayVo>,
    calendarDays: List<CalendarDayInfoVo>
): Int {
    return if (festivalDay == startDay) { // 축제 시작 일인 경우
        (festivalDays.lastOrNull()?.order ?: 0) + 1
    } else { // 축제 시작일이 아닌 경우
        val beforeDay = calendarDays.lastOrNull()
        val beforeFestivalDay = beforeDay
            ?.festivalDays
            ?.firstOrNull { beforeFestivalDay ->
                beforeFestivalDay.festivalId == festivalItem.festivalId
            }
        if (beforeFestivalDay?.weekRange?.first == weekRange.first) { // 축제 시작일의 주와 같은 주일 경우
            beforeFestivalDay.order
        } else { // 축제 시작일의 주와 같은 주가 아닐 경우
            festivalDays.size
        }
    }
}

/**
 * 축제 일정의 시작일과 종료일 범위
 * @param festivalCalendar 축제 일 기준 calendar
 */
private fun getFestivalRange(
    festivalCalendar: Calendar,
    startDay: Int,
    endDay: Int
): IntRange {
    return IntRange(
        festivalCalendar.dayOfYear(),
        festivalCalendar.dayOfYear() + (endDay - startDay)
    )
}

private fun getFestivalCountInWeek(
    festivalCalendar: Calendar,
    startDay: Int,
    endDay: Int,
    weekRange: IntRange
): Int {
    val festivalDayRange = getFestivalRange(
        festivalCalendar = festivalCalendar,
        startDay = startDay,
        endDay = endDay
    )

    var countOfWeek = 0
    festivalDayRange.forEach { dayOfFestival ->
        weekRange.forEach { dayOfWeek ->
            if (dayOfFestival == dayOfWeek) countOfWeek++
        }
    }
    return countOfWeek
}
