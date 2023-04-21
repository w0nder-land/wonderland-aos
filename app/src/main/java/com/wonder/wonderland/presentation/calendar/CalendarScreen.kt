package com.wonder.wonderland.presentation.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.wonderland.presentation.MainDestination
import com.wonder.wonderland.presentation.MainViewModel

@Composable
fun CalendarView(
    mainViewModel: MainViewModel,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    BackHandler {
        mainViewModel.selectBottomNavigationItem(MainDestination.HOME)
        onBackClick()
    }

    CalendarScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarScreen() {
    Scaffold(
        containerColor = Wonder500,
        topBar = {},
        content = { padding ->
            CalendarContent(modifier = Modifier.padding(padding))
        }
    )
}

@Composable
private fun CalendarContent(modifier: Modifier) {
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    WonderTheme {
        CalendarScreen()
    }
}
