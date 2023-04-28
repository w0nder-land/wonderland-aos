package com.imaec.feature.festival.item

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
internal fun FestivalTitleItemView(
    festivalName: String,
    festivalDescription: String?,
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = festivalName,
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

        if (festivalDescription != null) {
            Text(
                text = festivalDescription,
                style = Body1,
                color = Gray200
            )
        }
    }
}

@Preview
@Composable
private fun FestivalTitleItemViewPreview() {
    WonderTheme {
        FestivalTitleItemView(
            festivalName = "123",
            festivalDescription = ""
        )
    }
}
