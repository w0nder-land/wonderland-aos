package com.wonder.wonderland.presentation.calendar

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
import com.wonder.wonderland.presentation.calendar.model.FestivalDay

@Composable
internal fun CalendarDayView(
    day: String,
    festivalDaysGroup: Map<String, List<FestivalDay>>,
    isSunday: Boolean,
    isSaturday: Boolean = false,
    isToday: Boolean = false,
    isCurrentMonth: Boolean = true,
) {
    Column(
        modifier = Modifier
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
            festivalDaysGroup.forEach { group ->
                group.value.firstOrNull { festivalDay ->
                    festivalDay.day == day.toInt()
                }?.let { festivalDay ->
                    Box(
                        modifier = Modifier
                            .padding(
                                top = 18.dp * festivalDay.order + 2.dp,
                                start = startPadding(
                                    isSunday = isSunday,
                                    isStartDay = festivalDay.startDay == day.toInt()
                                ),
                                end = endPadding(
                                    isSaturday = isSaturday,
                                    isEndDay = festivalDay.endDay == day.toInt()
                                ),
                            )
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(
                                color = if (festivalDay.festivalName.toInt() % 3 == 0) {
                                    WonderBlue
                                } else if (festivalDay.festivalName.toInt() % 3 == 1) {
                                    WonderYellow
                                } else {
                                    Wonder100
                                },
                                shape = RoundedCornerShape(
                                    topStart = startCornerRadius(
                                        isSunday = isSunday,
                                        isStartDay = festivalDay.startDay == day.toInt()
                                    ),
                                    bottomStart = startCornerRadius(
                                        isSunday = isSunday,
                                        isStartDay = festivalDay.startDay == day.toInt()
                                    ),
                                    topEnd = endCornerRadius(
                                        isSaturday = isSaturday,
                                        isEndDay = festivalDay.endDay == day.toInt()
                                    ),
                                    bottomEnd = endCornerRadius(
                                        isSaturday = isSaturday,
                                        isEndDay = festivalDay.endDay == day.toInt()
                                    )
                                )
                            )
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = festivalDay.festivalName
                        )
                    }
                }
            }
        }
    }
}

private fun startPadding(
    isSunday: Boolean,
    isStartDay: Boolean
) = if (isSunday || isStartDay) 8.dp else 0.dp

private fun endPadding(
    isSaturday: Boolean,
    isEndDay: Boolean
) = if (isSaturday || isEndDay) 8.dp else 0.dp

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
            festivalDaysGroup = emptyMap(),
            isSunday = false
        )
    }
}
