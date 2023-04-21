package com.wonder.wonderland.presentation.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Caption2
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle1
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.component.ui.switch.WonderSwitch
import com.wonder.resource.R
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
        containerColor = Gray900,
        topBar = { CalendarTopBar() },
        content = { padding ->
            CalendarContent(modifier = Modifier.padding(padding))
        }
    )
}

@Composable
private fun CalendarTopBar() {
    var isInterestFestivalChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .singleClick {
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "2023년 1월",
                style = Subtitle1,
                color = White
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = null
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            WonderSwitch(
                checked = isInterestFestivalChecked,
                onCheckedChange = { isInterestFestivalChecked = !isInterestFestivalChecked }
            )

            Text(
                modifier = Modifier.singleClick(hasRipple = false) {
                    isInterestFestivalChecked = !isInterestFestivalChecked
                },
                text = "관심축제만 보기",
                style = Caption2,
                color = Gray50
            )
        }
    }
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
