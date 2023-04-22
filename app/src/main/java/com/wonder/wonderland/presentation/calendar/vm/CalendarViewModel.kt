package com.wonder.wonderland.presentation.calendar.vm

import com.imaec.model.FestivalInfo
import com.wonder.base.WonderViewModel
import com.wonder.component.util.getCurrentMonth
import com.wonder.component.util.toCalendar
import com.wonder.component.util.toDate
import com.wonder.component.util.toDay
import com.wonder.wonderland.presentation.calendar.model.CalendarDayInfo
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo
import com.wonder.wonderland.presentation.calendar.model.FestivalDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
internal class CalendarViewModel @Inject constructor() :
    WonderViewModel<CalendarEvent, CalendarResult, CalendarState, CalendarEffect>(CalendarState()) {

    init {
        processEvent(CalendarEvent.GetCurrentMonth)
    }

    override fun Flow<CalendarEvent>.toResults(): Flow<CalendarResult> = merge(
        filterIsInstance<CalendarEvent.GetCurrentMonth>().toGetCurrentMonthResult()
    )

    override fun CalendarResult.reduce(state: CalendarState): CalendarState {
        return when (this) {
            is CalendarResult.CurrentMonth -> {
                state.copy(calendarInfo = calendarInfo)
            }
        }
    }

    private fun Flow<CalendarEvent.GetCurrentMonth>.toGetCurrentMonthResult(): Flow<CalendarResult> =
        mapLatest {
            CalendarResult.CurrentMonth(
                calendarInfo = getCalendarInfo()
            )
        }

    private fun getCalendarInfo(): CalendarInfo {
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
        val festivals = getFestivals().sortedBy { it.startDate }

        val calendarDays = getCalendarDays(
            firstDay = 1,
            lastDay = lastDayOfMonth + 1,
            currentMonth = month,
            festivals = festivals
        )

        val beforeCalendarDays = getCalendarDays(
            firstDay = beforeMonthLastDayOfMonth - firstDayOfWeek + 1,
            lastDay = beforeMonthLastDayOfMonth,
            currentMonth = month - 1,
            festivals = festivals
        )

        val afterCalendarDays = getCalendarDays(
            firstDay = 1,
            lastDay = 7 - (firstDayOfWeek - 1 + lastDayOfMonth) % 7 + 1,
            currentMonth = month + 1,
            festivals = festivals
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

    private fun getCalendarDays(
        firstDay: Int,
        lastDay: Int,
        currentMonth: Int,
        festivals: List<FestivalInfo>,
    ): List<CalendarDayInfo> {
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
                    festivalDaysGroup = festivalDays.groupBy { it.festivalName }
                )
            )
        }
        return calendarDays
    }

    private fun getFestivals(): List<FestivalInfo> {
        return listOf(
            FestivalInfo(
                name = "1",
                startDate = "2023.04.01",
                endDate = "2023.04.02"
            ),
            FestivalInfo(
                name = "2",
                startDate = "2023.04.01",
                endDate = "2023.04.6"
            ),
            FestivalInfo(
                name = "3",
                startDate = "2023.04.1",
                endDate = "2023.04.4"
            ),
            FestivalInfo(
                name = "4",
                startDate = "2023.04.1",
                endDate = "2023.04.5"
            ),
            FestivalInfo(
                name = "5",
                startDate = "2023.04.1",
                endDate = "2023.04.3"
            ),
            FestivalInfo(
                name = "6",
                startDate = "2023.04.1",
                endDate = "2023.04.1"
            ),
            FestivalInfo(
                name = "7",
                startDate = "2023.04.13",
                endDate = "2023.04.17"
            ),
            FestivalInfo(
                name = "8",
                startDate = "2023.04.20",
                endDate = "2023.04.21"
            ),
        )
    }
}

const val checkName = "7"
