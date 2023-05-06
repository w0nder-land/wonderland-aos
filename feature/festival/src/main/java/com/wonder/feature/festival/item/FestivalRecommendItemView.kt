package com.wonder.feature.festival.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Gray100
import com.wonder.component.theme.Heading2
import com.wonder.component.ui.common.RecommendFestivalView
import com.wonder.resource.R

@Composable
internal fun FestivalRecommendItemView() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "추천 공연",
            style = Heading2,
            color = Gray100
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) {
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
    }
}
