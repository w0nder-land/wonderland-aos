package com.wonder.wonderland.presentation.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wonder.component.theme.Caption2
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Suit
import com.wonder.component.theme.Sunday
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.util.month
import com.wonder.component.util.year
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CalendarColumn(
    calendarListState: LazyListState,
    calendarInfo: CalendarInfo,
    onFestivalClick: (festivalId: Int) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val listState = rememberSaveable(saver = LazyListState.Saver) { calendarListState }
    val weeks = remember { mutableListOf("일", "월", "화", "수", "목", "금", "토") }
    val weekOfMonth = calendarInfo.calendarDays.chunked(7)
    val calendar = Calendar.getInstance()

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            item {
                LazyRow {
                    items(weeks) {
                        Text(
                            modifier = Modifier.width(screenWidth / 7),
                            text = it,
                            style = Caption2,
                            color = if (it == "일") Sunday else White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            items(weekOfMonth) { days ->
                Box {
                    LazyRow {
                        itemsIndexed(days) { index, day ->
                            val isCurrentMonth = day.year == calendarInfo.year &&
                                day.month == calendarInfo.month
                            val isToday = calendar.year() == day.year &&
                                calendar.month() == day.month &&
                                calendarInfo.today == day.day
                            CalendarDayView(
                                modifier = Modifier.width(screenWidth / 7),
                                day = "${day.day}",
                                festivalDays = day.festivalDays,
                                isSunday = index % 7 == 0,
                                isSaturday = index % 7 == 6,
                                isToday = isToday,
                                isCurrentMonth = isCurrentMonth,
                                onFestivalClick = onFestivalClick
                            )
                        }
                    }

                    days.forEachIndexed { index, calendarDayInfo ->
                        calendarDayInfo.festivalDays.forEach { festivalDay ->
                            val isSunday = index % 7 == 0
                            if (festivalDay.isStartDay || isSunday) {
                                val dayWidth = screenWidth / 7
                                val festivalCountInWeek = festivalDay.festivalCountInWeek
                                val sundayMargin = if (isSunday) 8.dp else 0.dp
                                val saturdayMargin = if (index % 7 == 6) 8.dp else 0.dp
                                val extraMargin = sundayMargin + saturdayMargin
                                val x = dayWidth * index + sundayMargin
                                val y = 36.dp + (18.dp * festivalDay.order)

                                Text(
                                    modifier = Modifier
                                        .width(dayWidth * festivalCountInWeek - extraMargin)
                                        .padding(start = 4.dp)
                                        .offset(x = x, y = y),
                                    text = festivalDay.festivalName,
                                    style = TextStyle(
                                        fontFamily = Suit,
                                        fontWeight = FontWeight.W600,
                                        fontSize = 10.sp,
                                        lineHeight = 16.sp,
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    ),
                                    color = Gray800,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarColumnPreview() {
    WonderTheme {
        CalendarColumn(
            calendarListState = rememberLazyListState(),
            calendarInfo = CalendarInfo(),
            onFestivalClick = {}
        )
    }
}
