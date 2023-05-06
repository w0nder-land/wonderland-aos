package com.wonder.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.feature.home.vm.HomeViewModel
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onFestivalClick: () -> Unit,
) {
    HomeScreen(
        onFestivalClick = onFestivalClick
    )
}

@Composable
private fun HomeScreen(
    onFestivalClick: () -> Unit,
) {
    Scaffold(
        containerColor = Gray900,
        topBar = {},
        content = { padding ->
            HomeContent(
                modifier = Modifier.padding(padding),
                onFestivalClick = onFestivalClick
            )
        }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier,
    onFestivalClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .background(
                    color = Wonder500,
                    shape = RoundedCornerShape(10.dp)
                )
                .singleClick { onFestivalClick() }
                .padding(16.dp)
                .align(Alignment.Center),
            text = "상세",
            style = Body2,
            color = White
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    WonderTheme {
        HomeScreen(
            onFestivalClick = {}
        )
    }
}
