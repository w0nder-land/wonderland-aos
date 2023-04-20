package com.wonder.wonderland.ui.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Body1
import com.wonder.component.theme.Gray100
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.textfield.BasicTextField
import com.wonder.resource.R
import com.wonder.wonderland.ui.MainDestination
import com.wonder.wonderland.ui.MainViewModel

@Composable
fun SearchView(
    mainViewModel: MainViewModel,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    BackHandler {
        mainViewModel.selectBottomNavigationItem(MainDestination.HOME)
        onBackClick()
    }

    SearchScreen(
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
        ),
        onSearch = searchViewModel::searchFestival
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    recentKeywords: List<String>,
    popularKeywords: List<String>,
    onSearch: (keyword: String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = Gray900,
        topBar = {
            SearchTopBar(
                onSearch = onSearch
            )
        },
        content = { padding ->
            SearchContent(
                modifier = Modifier.padding(padding),
                recentKeywords = recentKeywords,
                popularKeywords = popularKeywords
            )
        }
    )
}

@Composable
fun SearchTopBar(
    onSearch: (keyword: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyword = remember { mutableStateOf("") }
    val searchAction = {
        focusManager.clearFocus()
        onSearch(keyword.value)
    }

    BasicTextField(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
        textState = remember { mutableStateOf("") },
        textStyle = Subtitle3,
        textColor = Gray50,
        hintColor = Gray400,
        background = Gray700,
        strokeColor = Gray700,
        shape = CircleShape,
        placeholder = "어떤 축제를 찾고 계신가요?",
        icon = R.drawable.ic_search,
        keyboardActions = KeyboardActions(
            onSearch = { searchAction() }
        ),
        onIconClick = { searchAction() }
    )
}

@Composable
private fun SearchContent(
    modifier: Modifier,
    recentKeywords: List<String>,
    popularKeywords: List<String>,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
}

@Preview
@Composable
private fun SearchScreenPreview() {
    WonderTheme {
        SearchScreen(
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
            ),
            onSearch = {}
        )
    }
}
