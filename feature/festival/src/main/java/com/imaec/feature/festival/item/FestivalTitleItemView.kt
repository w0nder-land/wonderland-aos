package com.imaec.feature.festival

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wonder.component.theme.Body1
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Suit
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme

@Composable
internal fun FestivalTitleItemView() {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Haus of Wonder",
            style = TextStyle(
                fontFamily = Suit,
                fontWeight = FontWeight.W700,
                fontSize = 32.sp,
                lineHeight = 36.sp,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            color = White
        )

        Text(
            text = "국내외 최정상 뮤지션들이 참여하는 음악 페스티벌로 " +
                "다양한 서비스를 선보이는 원더월이 개최하는 첫 음악 페스티벌이다.",
            style = Body1,
            color = Gray200
        )
    }
}

@Preview
@Composable
private fun FestivalTitleItemViewPreview() {
    WonderTheme {
        FestivalTitleItemView()
    }
}
