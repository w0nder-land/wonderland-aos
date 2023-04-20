package com.wonder.wonderland.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.wonderland.ui.MainActivity
import com.wonder.wonderland.ui.MainViewModel
import dagger.hilt.android.internal.managers.FragmentComponentManager

@Composable
fun HomeView(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val mainActivity = FragmentComponentManager.findActivity(LocalContext.current) as MainActivity

    BackHandler { mainActivity.finish() }

    HomeScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen() {
    Scaffold(
        containerColor = Gray900,
        topBar = {},
        content = { padding ->
            HomeContent(modifier = Modifier.padding(padding))
        }
    )
}

@Composable
private fun HomeContent(modifier: Modifier) {
}

@Preview
@Composable
private fun HomeScreenPreview() {
    WonderTheme {
        HomeScreen()
    }
}
