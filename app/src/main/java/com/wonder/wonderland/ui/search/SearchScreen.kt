package com.wonder.wonderland.ui.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.WonderTheme
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

    SearchScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen() {
    Scaffold(
        containerColor = Gray900,
        topBar = {},
        content = { padding ->
            SearchContent(modifier = Modifier.padding(padding))
        }
    )
}

@Composable
private fun SearchContent(modifier: Modifier) {
}

@Preview
@Composable
private fun SearchScreenPreview() {
    WonderTheme {
        SearchScreen()
    }
}
