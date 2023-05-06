package com.wonder.feature.search.keyword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@Composable
fun SearchRecentKeywordView(
    recentKeywords: List<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "최근 검색어",
                style = Subtitle2,
                color = Gray400
            )

            Text(
                text = "지우기",
                style = Subtitle3,
                color = Gray200
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recentKeywords) { keyword ->
                SearchRecentKeywordItemView(keyword = keyword)
            }
        }
    }
}

@Composable
private fun SearchRecentKeywordItemView(keyword: String) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .background(
                color = Gray800,
                shape = CircleShape
            )
            .singleClick(shape = CircleShape) {
            }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = keyword,
            style = Body2,
            color = Gray50
        )

        Icon(
            modifier = Modifier.singleClick(shape = CircleShape) {
            },
            painter = painterResource(id = R.drawable.ic_close),
            tint = Gray400,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
private fun SearchRecentKeywordItemViewPreview() {
    WonderTheme {
        SearchRecentKeywordView(
            recentKeywords = listOf(
                "서울뮤직페스티벌",
                "워터밤 서울",
                "재즈페스티벌"
            )
        )
    }
}
