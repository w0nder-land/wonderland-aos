package com.wonder.wonderland.presentation.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.component.ui.tab.SlideTab
import com.wonder.component.ui.textfield.BasicTextField
import com.wonder.component.util.Keyboard
import com.wonder.component.util.rememberKeyboardState
import com.wonder.resource.R
import com.wonder.wonderland.presentation.MainDestination
import com.wonder.wonderland.presentation.MainViewModel
import com.wonder.wonderland.presentation.search.keyword.searchKeywordView
import com.wonder.wonderland.presentation.search.result.searchResultView

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
        tabs = listOf(
            "전체",
            "뮤직 페스티벌",
            "인디/록",
            "내한공연",
            "힙합/EDM"
        ),
        festivals = listOf("1", "2", "3", "4", "5", "6", "7"),
        onSearch = searchViewModel::searchFestival,
        onTabClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    recentKeywords: List<String>,
    popularKeywords: List<String>,
    tabs: List<String>,
    festivals: List<String>,
    onSearch: (keyword: String) -> Unit,
    onTabClick: (tabIndex: Int) -> Unit
) {
    var isKeywordView by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.imePadding(),
        containerColor = Gray900,
        topBar = {
            SearchTopBar(
                onSearch = {
                    isKeywordView = !isKeywordView
                    onSearch(it)
                }
            )
        },
        content = { padding ->
            SearchContent(
                modifier = Modifier.padding(padding),
                isKeywordView = isKeywordView,
                recentKeywords = recentKeywords,
                popularKeywords = popularKeywords,
                tabs = tabs,
                festivals = festivals,
                onTabClick = onTabClick
            )
        }
    )
}

@Composable
fun SearchTopBar(
    onSearch: (keyword: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardState = rememberKeyboardState()
    val keyword = remember { mutableStateOf("") }
    var focusState by remember { mutableStateOf(false) }
    val searchAction = {
        focusManager.clearFocus()
        onSearch(keyword.value)
    }

    LaunchedEffect(keyboardState) {
        if (keyboardState == Keyboard.Closed) focusManager.clearFocus()
    }

    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (focusState || keyword.value.isNotBlank()) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .singleClick(shape = CircleShape) { focusManager.clearFocus() }
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    tint = Gray50,
                    contentDescription = null
                )
            }
        }

        BasicTextField(
            modifier = Modifier
                .padding(vertical = 7.dp)
                .onFocusEvent { focusState = it.isFocused },
            textState = keyword,
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
}

@Composable
private fun SearchContent(
    modifier: Modifier,
    isKeywordView: Boolean,
    recentKeywords: List<String>,
    popularKeywords: List<String>,
    tabs: List<String>,
    festivals: List<String>,
    onTabClick: (tabIndex: Int) -> Unit,
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    if (!isKeywordView) {
        SlideTab(
            modifier = modifier,
            tabs = tabs,
            selectedIndex = selectedTabIndex
        ) {
            selectedTabIndex = it
            onTabClick(it)
        }
    }

    LazyColumn(
        modifier = modifier.padding(top = if (isKeywordView) 0.dp else 45.dp),
        contentPadding = PaddingValues(vertical = if (isKeywordView) 20.dp else 12.dp),
        verticalArrangement = Arrangement.spacedBy(if (isKeywordView) 16.dp else 0.dp)
    ) {
        if (isKeywordView) {
            searchKeywordView(
                recentKeywords = recentKeywords,
                popularKeywords = popularKeywords
            )
        } else {
            searchResultView(
                festivals = festivals
            )
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
            tabs = listOf(
                "전체",
                "뮤직 페스티벌",
                "인디/록",
                "내한공연",
                "힙합/EDM"
            ),
            festivals = listOf(),
            onSearch = {},
            onTabClick = {}
        )
    }
}
