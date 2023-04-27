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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wonder.component.theme.Caption2
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle1
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.Subtitle3
import com.wonder.component.theme.Suit
import com.wonder.component.theme.Sunday
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.bottomsheet.BottomSheetTopDot
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.picker.WheelPicker
import com.wonder.component.ui.singleClick
import com.wonder.component.ui.switch.WonderSwitch
import com.wonder.resource.R
import com.wonder.wonderland.presentation.MainDestination
import com.wonder.wonderland.presentation.MainViewModel
import com.wonder.wonderland.presentation.calendar.filter.CalendarFilterDrawer
import com.wonder.wonderland.presentation.calendar.model.CalendarDayInfo
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo
import com.wonder.wonderland.presentation.calendar.vm.CalendarEvent
import com.wonder.wonderland.presentation.calendar.vm.CalendarState
import com.wonder.wonderland.presentation.calendar.vm.CalendarViewModel
import kotlinx.coroutines.launch

@Composable
internal fun CalendarView(
    mainViewModel: MainViewModel,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val calendarState = calendarViewModel.states.collectAsStateWithLifecycle().value
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            mainViewModel.selectBottomNavigationItem(MainDestination.HOME)
            onBackClick()
        }
    }

    LaunchedEffect(Unit) {
        calendarViewModel.processEvent(CalendarEvent.GetCurrentYearMonth)
    }

    LaunchedEffect(calendarState.currentYearMonth) {
        if (calendarState.currentYearMonth.isEmpty()) return@LaunchedEffect

        calendarViewModel.processEvent(
            event = CalendarEvent.SearchFestivals(calendarState.currentYearMonth)
        )
    }

    CalendarScreen(
        calendarState = calendarState,
        drawerState = drawerState,
        onSelectYearMonth = { yearMonth ->
            calendarViewModel.processEvent(CalendarEvent.UpdateCurrentYearMonth(yearMonth))
        }
    )
}

@Composable
private fun CalendarScreen(
    calendarState: CalendarState,
    drawerState: DrawerState,
    onSelectYearMonth: (yearMonth: String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            modifier = Modifier,
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    CalendarFilterDrawer()
                }
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Scaffold(
                    containerColor = Gray900,
                    topBar = {
                        CalendarTopBar(
                            currentMonth = calendarState.currentYearMonth,
                            yearMonthItems = calendarState.yearMonthItems,
                            onSelectYearMonth = onSelectYearMonth
                        )
                    },
                    content = { padding ->
                        if (!calendarState.isLoading) {
                            Box {
                                CalendarContent(
                                    modifier = Modifier.padding(padding),
                                    calendarInfo = calendarState.calendarInfo
                                )

                                CalendarFilterView(
                                    modifier = Modifier
                                        .padding(padding)
                                        .align(Alignment.BottomCenter),
                                    onFilterClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun CalendarTopBar(
    currentMonth: String,
    yearMonthItems: List<String>,
    onSelectYearMonth: (yearMonth: String) -> Unit,
) {
    var isInterestFestivalChecked by remember { mutableStateOf(false) }
    var isShowSelectMonthBottomSheet by remember { mutableStateOf(false) }

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
                .singleClick(shape = RoundedCornerShape(8.dp)) {
                    isShowSelectMonthBottomSheet = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = currentMonth,
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

    if (isShowSelectMonthBottomSheet) {
        SelectMonthBottomSheetDialog(
            currentMonth = currentMonth,
            yearMonthItems = yearMonthItems,
            onSelectYearMonth = onSelectYearMonth,
            onBottomSheetClose = { isShowSelectMonthBottomSheet = false }
        )
    }
}

@Composable
private fun CalendarContent(
    modifier: Modifier,
    calendarInfo: CalendarInfo
) {
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

        itemsIndexed(calendarInfo.beforeCalendarDays) { index, day ->
            CalendarDayView(
                day = day.day,
                festivalDays = day.festivalDays,
                isSunday = index == 0,
                isCurrentMonth = false
            )
        }

        itemsIndexed(calendarInfo.calendarDays) { index, day ->
            CalendarDayView(
                day = day.day,
                festivalDays = day.festivalDays,
                isSunday = index % 7 == 7 - calendarInfo.beforeMonthDayCount,
                isSaturday = index % 7 == 7 - calendarInfo.beforeMonthDayCount - 1,
                isToday = calendarInfo.today == (index + 1)
            )
        }

        itemsIndexed(calendarInfo.afterCalendarDays) { index, day ->
            CalendarDayView(
                day = day.day,
                festivalDays = day.festivalDays,
                isSunday = false,
                isSaturday = index == calendarInfo.afterMonthDayCount - 1,
                isCurrentMonth = false
            )
        }
    }
}

@Composable
private fun CalendarFilterView(
    modifier: Modifier,
    onFilterClick: () -> Unit,
) {
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(bottom = 16.dp)
            .size(width = 83.dp, height = 44.dp)
            .background(
                color = if (isSelected) White else Gray900,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = White,
                shape = CircleShape
            )
            .singleClick(shape = CircleShape) {
                onFilterClick()
                isSelected = !isSelected
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectMonthBottomSheetDialog(
    currentMonth: String,
    yearMonthItems: List<String>,
    onSelectYearMonth: (yearMonth: String) -> Unit,
    onBottomSheetClose: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var selectedIndex by remember { mutableStateOf(yearMonthItems.indexOf(currentMonth)) }
    val gradientColors = listOf(
        Gray800.copy(alpha = 1f),
        Gray800.copy(alpha = 0f)
    )

    ModalBottomSheet(
        onDismissRequest = onBottomSheetClose,
        sheetState = sheetState,
        dragHandle = {
            BottomSheetTopDot()
        },
        containerColor = Gray800
    ) {
        Column(modifier = Modifier.height(346.dp)) {
            Box {
                WheelPicker(
                    startIndex = selectedIndex,
                    menuCount = yearMonthItems.size,
                    isInfinite = false,
                    onCurrentIndex = { selectedIndex = it },
                    content = { index ->
                        Text(
                            text = yearMonthItems[index],
                            style = TextStyle(
                                fontFamily = Suit,
                                fontWeight = FontWeight.W500,
                                fontSize = 24.sp,
                                lineHeight = 36.sp,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                )
                            ),
                            color = White
                        )
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .background(brush = Brush.verticalGradient(colors = gradientColors))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .background(
                            brush = Brush.verticalGradient(colors = gradientColors.reversed())
                        )
                        .align(Alignment.BottomCenter)
                )
            }

            HorizontalDivider(color = Gray600)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(76.dp)
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            color = White,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .singleClick {
                            onSelectYearMonth(yearMonthItems[selectedIndex])
                            onBottomSheetClose()
                        }
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "확인",
                        style = Subtitle2,
                        color = Gray800
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    val calendarDays = mutableListOf<CalendarDayInfo>().apply {
        repeat(30) {
            add(CalendarDayInfo(day = "${it + 1}"))
        }
    }
    val beforeCalendarDays = mutableListOf<CalendarDayInfo>().apply {
        repeat(6) {
            add(CalendarDayInfo(day = "${it + 21}"))
        }
    }
    val afterCalendarDays = mutableListOf<CalendarDayInfo>().apply {
        repeat(6) {
            add(CalendarDayInfo(day = "${it + 1}"))
        }
    }
    WonderTheme {
        CalendarScreen(
            calendarState = CalendarState(
                isLoading = false,
                currentYearMonth = "2023년 4월",
                calendarInfo = CalendarInfo(
                    today = 22,
                    firstDayOfWeek = 7,
                    lastDayOfMonth = 30,
                    calendarDays = calendarDays,
                    beforeCalendarDays = beforeCalendarDays,
                    afterCalendarDays = afterCalendarDays
                )
            ),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            onSelectYearMonth = {}
        )
    }
}

@Preview
@Composable
private fun CalendarScreenDrawerPreview() {
    val calendarDays = mutableListOf<CalendarDayInfo>().apply {
        repeat(30) {
            add(CalendarDayInfo(day = "${it + 1}"))
        }
    }
    val beforeCalendarDays = mutableListOf<CalendarDayInfo>().apply {
        repeat(6) {
            add(CalendarDayInfo(day = "${it + 21}"))
        }
    }
    val afterCalendarDays = mutableListOf<CalendarDayInfo>().apply {
        repeat(6) {
            add(CalendarDayInfo(day = "${it + 1}"))
        }
    }
    WonderTheme {
        CalendarScreen(
            calendarState = CalendarState(
                isLoading = false,
                currentYearMonth = "2023년 4월",
                calendarInfo = CalendarInfo(
                    today = 22,
                    firstDayOfWeek = 7,
                    lastDayOfMonth = 30,
                    calendarDays = calendarDays,
                    beforeCalendarDays = beforeCalendarDays,
                    afterCalendarDays = afterCalendarDays
                )
            ),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            onSelectYearMonth = {}
        )
    }
}
