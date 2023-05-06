package com.wonder.wonderland.presentation.my

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.WonderTheme

@Composable
fun MyView(
    myViewModel: MyViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    BackHandler { onBackClick() }

    MyScreen()
}

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
