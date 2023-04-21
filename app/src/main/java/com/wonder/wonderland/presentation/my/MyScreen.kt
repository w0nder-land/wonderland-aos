package com.wonder.wonderland.presentation.my

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
import com.wonder.wonderland.presentation.MainDestination
import com.wonder.wonderland.presentation.MainViewModel

@Composable
fun MyView(
    mainViewModel: MainViewModel,
    myViewModel: MyViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    BackHandler {
        mainViewModel.selectBottomNavigationItem(MainDestination.HOME)
        onBackClick()
    }

    MyScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyScreen() {
    Scaffold(
        containerColor = Gray900,
        topBar = {},
        content = { padding ->
            MyContent(modifier = Modifier.padding(padding))
        }
    )
}

@Composable
private fun MyContent(modifier: Modifier) {
}

@Preview
@Composable
private fun MyScreenPreview() {
    WonderTheme {
        MyScreen()
    }
}
