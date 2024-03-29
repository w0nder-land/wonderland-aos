package com.wonder.feature.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Caption2
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Sunday
import com.wonder.component.theme.SundayDisable
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder100
import com.wonder.component.theme.WonderBlue
import com.wonder.component.theme.WonderTheme
import com.wonder.component.theme.WonderYellow
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.singleClick
import com.wonder.feature.calendar.model.FestivalDayVo

@Composable
internal fun CalendarDayView(
    modifier: Modifier = Modifier,
    day: String,
    festivalDays: List<FestivalDayVo>,
    isSunday: Boolean,
    isSaturday: Boolean = false,
    isToday: Boolean = false,
    isCurrentMonth: Boolean = true,
    onFestivalClick: (festivalId: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = 8.dp)
            .defaultMinSize(minHeight = 113.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(color = Gray700)

        Box(
            modifier = Modifier
                .padding(top = 2.dp, bottom = 1.dp)
                .size(21.dp)
                .background(
                    color = if (isToday) Gray400 else Color.Unspecified,
                    shape = CircleShape
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = day,
                style = Caption2,
                color = if (isCurrentMonth) {
                    if (isSunday) Sunday else White
                } else {
                    if (isSunday) SundayDisable else Gray400
                }
            )
        }

        Box {
            festivalDays.forEachIndexed { index, festivalDay ->
                Box(
                    modifier = Modifier
                        .padding(
                            top = 18.dp * festivalDay.order + 2.dp,
                            start = startPadding(
                                isSunday = isSunday,
                                isStartDay = festivalDay.isStartDay
                            ),
                            end = endPadding(
                                isSaturday = isSaturday,
                                isEndDay = festivalDay.isEndDay
                            ),
                        )
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(
                            color = if (index % 3 == 0) {
                                WonderBlue
                            } else if (index % 3 == 1) {
                                WonderYellow
                            } else {
                                Wonder100
                            },
                            shape = RoundedCornerShape(
                                topStart = startCornerRadius(
                                    isSunday = isSunday,
                                    isStartDay = festivalDay.isStartDay
                                ),
                                bottomStart = startCornerRadius(
                                    isSunday = isSunday,
                                    isStartDay = festivalDay.isStartDay
                                ),
                                topEnd = endCornerRadius(
                                    isSaturday = isSaturday,
                                    isEndDay = festivalDay.isEndDay
                                ),
                                bottomEnd = endCornerRadius(
                                    isSaturday = isSaturday,
                                    isEndDay = festivalDay.isEndDay
                                )
                            )
                        )
                        .singleClick { onFestivalClick(festivalDay.festivalId) }
                )
            }
        }
    }
}

private fun startPadding(
    isSunday: Boolean,
    isStartDay: Boolean
) = if (isSunday) {
    8.dp
} else {
    if (isStartDay) 1.dp else 0.dp
}

private fun endPadding(
    isSaturday: Boolean,
    isEndDay: Boolean
) = if (isSaturday) {
    8.dp
} else {
    if (isEndDay) 1.dp else 0.dp
}

private fun startCornerRadius(
    isSunday: Boolean,
    isStartDay: Boolean
) = if (isSunday || isStartDay) 2.dp else 0.dp

private fun endCornerRadius(
    isSaturday: Boolean,
    isEndDay: Boolean
) = if (isSaturday || isEndDay) 2.dp else 0.dp

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
private fun CalendarDayViewPreview() {
    WonderTheme {
        CalendarDayView(
            day = "22",
            festivalDays = listOf(
                FestivalDayVo(
                    festivalId = 1,
                    festivalName = "live SUM 2023 : 예빛, 허회경, 정새벽",
                    year = 2023,
                    month = 4,
                    day = 22,
                    isStartDay = true,
                    isEndDay = true,
                    weekRange = IntRange(0, 1),
                    festivalCountInWeek = 0,
                    order = 0
                )
            ),
            isSunday = false,
            onFestivalClick = {}
        )
    }
}
