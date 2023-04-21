package com.wonder.wonderland.presentation.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wonder.component.theme.Caption2
import com.wonder.component.theme.Gray400
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle1
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.Sunday
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.singleClick
import com.wonder.component.ui.switch.WonderSwitch
import com.wonder.resource.R
import com.wonder.wonderland.presentation.MainDestination
import com.wonder.wonderland.presentation.MainViewModel
import java.util.Random

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
            Box {
                CalendarContent(modifier = Modifier.padding(padding))

                CalendarFilterView(
                    modifier = Modifier
                        .padding(padding)
                        .align(Alignment.BottomCenter)
                )
            }
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
    val today = Random().nextInt(30)
    val weeks = listOf("일", "월", "화", "수", "목", "금", "토")

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(weeks) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                style = Caption2,
                color = if (it == "일") Sunday else White,
                textAlign = TextAlign.Center
            )
        }

        items(35) { index ->
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .height(113.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                HorizontalDivider(color = Gray700)

                Box(
                    modifier = Modifier
                        .size(21.dp)
                        .background(
                            color = if (today == index) Gray400 else Color.Unspecified,
                            shape = CircleShape
                        )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "${index + 1}",
                        style = Caption2,
                        color = if (index % 7 == 0) Sunday else White
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarFilterView(
    modifier: Modifier
) {
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(bottom = 16.dp)
            .size(width = 83.dp, height = 44.dp)
            .background(
                color = if (isSelected) White else Color.Unspecified,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = White,
                shape = CircleShape
            )
            .singleClick(shape = CircleShape) { isSelected = !isSelected }
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_filter),
                tint = if (isSelected) Gray900 else White,
                contentDescription = null
            )

            Text(
                text = "필터",
                style = Subtitle3,
                color = if (isSelected) Gray900 else White
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp, top = 11.dp)
                    .size(6.dp)
                    .background(
                        color = Wonder500,
                        shape = CircleShape
                    )
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    WonderTheme {
        CalendarScreen()
    }
}
