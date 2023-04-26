package com.imaec.feature.festival.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Gray100
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Heading2
import com.wonder.component.theme.WonderTheme
import com.wonder.resource.R

@Composable
internal fun FestivalGuestItemView() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "출연진",
            style = Heading2,
            color = Gray100
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(10) {
                FestivalGuestProfileView()
            }
        }
    }
}

@Composable
private fun FestivalGuestProfileView() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.img_sample_guest_1),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Text(
            modifier = Modifier.width(80.dp),
            text = "다운",
            style = Body2,
            color = Gray200,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun FestivalGuestItemViewPreview() {
    WonderTheme {
        FestivalGuestItemView()
    }
}
