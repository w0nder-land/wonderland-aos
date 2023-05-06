package com.wonder.feature.search.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body1
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Caption1
import com.wonder.component.theme.Gray100
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Heading2
import com.wonder.component.theme.Subtitle1
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.common.RecommendFestivalView
import com.wonder.component.ui.singleClick
import com.wonder.resource.R
import java.util.Random

fun LazyListScope.searchResultView(
    festivals: List<String>
) {
    items(festivals) { festival ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .singleClick {
                }
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            Image(
                modifier = Modifier.size(width = 69.dp, height = 92.dp),
                painter = painterResource(
                    id = listOf(
                        R.drawable.img_sample_festival_1,
                        R.drawable.img_sample_festival_2,
                        R.drawable.img_sample_festival_3
                    ).random()
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (Random().nextInt(10) < 3) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = Gray700,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                        text = "공연 종료",
                        style = Caption1,
                        color = Gray300
                    )
                }

                Text(
                    text = "서울재즈페스티벌 2023",
                    style = Subtitle1,
                    color = Gray50
                )

                Text(
                    text = "2023.01.01 (일)~ 01.02 (월)",
                    style = Body2,
                    color = Gray300
                )
            }
        }
    }
}

fun LazyListScope.searchEmptyView(
    recommendFestivals: List<String>
) {
    item {
        Column {
            Text(
                modifier = Modifier
                    .padding(top = 84.dp)
                    .fillMaxWidth(),
                text = "아쉽지만 검색 결과가 없어요\n비슷한 걸 찾아봤어요!",
                style = Body1,
                color = Gray100,
                textAlign = TextAlign.Center
            )

            Icon(
                modifier = Modifier
                    .padding(end = 40.dp)
                    .size(72.dp)
                    .align(Alignment.End),
                painter = painterResource(id = R.drawable.ic_arrow_empty_search),
                tint = White,
                contentDescription = null
            )
        }
    }

    item {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, top = 24.dp),
            text = "이런 축제는 어때요?",
            style = Heading2,
            color = Gray100
        )

        LazyRow(
            modifier = Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(recommendFestivals) { festival ->
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

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
private fun SearchResultViewPreview() {
    WonderTheme {
        LazyColumn {
            searchResultView(
                festivals = listOf("1", "2", "3", "4", "5", "6", "7")
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
private fun SearchEmptyViewPreview() {
    WonderTheme {
        LazyColumn {
            searchEmptyView(
                recommendFestivals = listOf("1", "2", "3", "4", "5", "6", "7")
            )
        }
    }
}
