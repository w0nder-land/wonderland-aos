package com.wonder.component.ui.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray500
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme

@Composable
fun SlideTab(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    hasDivider: Boolean = false,
    selectedIndex: Int,
    selectedListener: (selectedIndex: Int) -> Unit = {},
) {
    Column(
        modifier = modifier.then(Modifier.fillMaxWidth())
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedIndex,
            backgroundColor = Gray900,
            divider = {}
        ) {
            tabs.forEachIndexed { tabIndex, tab ->
                WonderTab(
                    isTabTextEmpty = tab.isEmpty(),
                    selected = selectedIndex == tabIndex,
                    selectedContentColor = Gray50,
                    unselectedContentColor = Gray500,
                    onClick = { selectedListener(tabIndex) },
                    tab = {
                        Text(
                            text = tab,
                            style = Subtitle2
                        )
                    },
                )
            }
        }

        if (hasDivider) {
            androidx.compose.material3.Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = Gray50
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SlideTabPreview() {
    WonderTheme {
        SlideTab(
            tabs = listOf(
                "전체",
                "뮤직 페스티벌",
                "인디/록",
                "내한공연",
                "힙합/EDM"
            ),
            selectedIndex = 0,
        )
    }
}
