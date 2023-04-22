package com.wonder.wonderland.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider

@Composable
internal fun CalendarDayView(
    day: Int,
    isSunday: Boolean,
    isToday: Boolean = false,
    isCurrentMonth: Boolean = true,
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(113.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        HorizontalDivider(color = Gray700)

        Box(
            modifier = Modifier
                .size(21.dp)
                .background(
                    color = if (isToday) Gray400 else Color.Unspecified,
                    shape = CircleShape
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "$day",
                style = Caption2,
                color = if (isCurrentMonth) {
                    if (isSunday) Sunday else White
                } else {
                    if (isSunday) SundayDisable else Gray400
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
private fun CalendarDayViewPreview() {
    WonderTheme {
        CalendarDayView(
            day = 22,
            isSunday = false
        )
    }
}
