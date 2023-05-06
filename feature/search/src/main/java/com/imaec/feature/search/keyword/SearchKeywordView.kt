package com.imaec.feature.search.keyword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body1
import com.wonder.component.theme.Gray100
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick

fun LazyListScope.searchKeywordView(
    recentKeywords: List<String>,
    popularKeywords: List<String>,
) {
    if (recentKeywords.isNotEmpty()) {
        item {
            SearchRecentKeywordView(
                recentKeywords = recentKeywords
            )
        }
    }

    item {
        Text(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = if (recentKeywords.isNotEmpty()) 33.dp else 0.dp,
                    bottom = 2.dp
                ),
            text = "실시간 인기 검색어",
            style = Subtitle2,
            color = Gray400
        )
    }

    itemsIndexed(popularKeywords) { index, keyword ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .singleClick {
                }
                .padding(horizontal = 16.dp)
                .height(34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(21.dp),
                text = "${index + 1}",
                style = Subtitle3,
                color = Wonder500
            )

            Text(
                text = keyword,
                style = Body1,
                color = Gray100
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
private fun SearchKeywordViewPreview() {
    WonderTheme {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            searchKeywordView(
                recentKeywords = listOf(
                    "서울뮤직페스티벌",
                    "워터밤 서울",
                    "재즈페스티벌"
                ),
                popularKeywords = listOf(
                    "서울뮤직페스티벌",
                    "워터밤 서울",
                    "서울재즈페스티벌",
                    "뷰티풀 민트 라이프",
                    "Have A Nice Day",
                    "원픽 페스티벌",
                    "다나카 1st 내한 콘서트",
                    "해리 스타일스",
                    "선셋 롤러코스터"
                )
            )
        }
    }
}
