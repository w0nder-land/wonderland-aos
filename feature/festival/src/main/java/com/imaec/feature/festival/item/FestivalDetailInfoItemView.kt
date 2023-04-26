package com.imaec.feature.festival.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Gray100
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Heading2
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.resource.R

@Composable
internal fun FestivalDetailInfoItemView() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "출연진",
            style = Heading2,
            color = Gray100
        )

        Box {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.img_sample_festival_detail),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )

            val colors = listOf(
                Gray900.copy(alpha = 0f),
                Gray900.copy(alpha = 0.4323f),
                Gray900.copy(alpha = 1f)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp)
                    .background(
                        brush = Brush.verticalGradient(colors = colors)
                    )
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .height(48.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_arrow_down),
                        tint = White,
                        contentDescription = null
                    )
                }
            }
        }

        HorizontalDivider(
            heightSize = 8.dp,
            color = Gray800
        )
    }
}

@Preview
@Composable
private fun FestivalDetailInfoItemViewPreview() {
    WonderTheme {
        FestivalDetailInfoItemView()
    }
}
