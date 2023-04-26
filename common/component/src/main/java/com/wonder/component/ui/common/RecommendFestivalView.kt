package com.wonder.component.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@Composable
fun RecommendFestivalView(
    festivalImage: Int,
    festivalTitle: String,
    onFestivalClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .singleClick { onFestivalClick() },
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Image(
            modifier = Modifier.size(width = 120.dp, height = 160.dp),
            painter = painterResource(id = festivalImage),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Text(
            text = festivalTitle,
            style = Subtitle3,
            color = Gray200
        )
    }
}

@Preview
@Composable
private fun RecommendFestivalViewPreview() {
    WonderTheme {
        RecommendFestivalView(
            festivalImage = listOf(
                R.drawable.img_sample_festival_1,
                R.drawable.img_sample_festival_2,
                R.drawable.img_sample_festival_3
            ).random(),
            festivalTitle = "서울재즈페스티벌 2023",
            onFestivalClick = {}
        )
    }
}
