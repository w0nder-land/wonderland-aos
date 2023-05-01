package com.wonder.wonderland.presentation.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wonder.component.theme.Caption2
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle1
import com.wonder.component.theme.Suit
import com.wonder.component.theme.Sunday
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.component.ui.switch.WonderSwitch
import com.wonder.component.util.month
import com.wonder.component.util.year
import com.wonder.resource.R
import com.wonder.wonderland.presentation.MainDestination
import com.wonder.wonderland.presentation.MainViewModel
import com.wonder.wonderland.presentation.calendar.bottomsheet.SelectMonthBottomSheetDialog
import com.wonder.wonderland.presentation.calendar.filter.CalendarFilter
import com.wonder.wonderland.presentation.calendar.filter.CalendarFilterButton
import com.wonder.wonderland.presentation.calendar.filter.CalendarFilterDrawer
import com.wonder.wonderland.presentation.calendar.model.CalendarDayInfo
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo
import com.wonder.wonderland.presentation.calendar.model.FestivalDay
import com.wonder.wonderland.presentation.calendar.model.FestivalDayWithOffset
import com.wonder.wonderland.presentation.calendar.vm.CalendarEffect
import com.wonder.wonderland.presentation.calendar.vm.CalendarEvent
import com.wonder.wonderland.presentation.calendar.vm.CalendarState
import com.wonder.wonderland.presentation.calendar.vm.CalendarViewModel
import com.wonder.wonderland.presentation.calendar.vm.isFilterChanged
import com.wonder.wonderland.presentation.calendar.vm.isFilterSelected
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
internal fun CalendarView(
    mainViewModel: MainViewModel,
    calendarViewModel: CalendarViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onMoveFestival: (festivalId: Int) -> Unit,
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
        if (calendarState.currentYearMonth.isEmpty()) {
            calendarViewModel.processEvent(CalendarEvent.GetCurrentYearMonth)
        }

        calendarViewModel.effects.collect { effect ->
            when (effect) {
                is CalendarEffect.MoveFestival -> onMoveFestival(effect.festivalId)
            }
        }
    }

    LaunchedEffect(calendarState.currentYearMonth, calendarState.isInterest) {
        if (calendarState.currentYearMonth.isEmpty()) return@LaunchedEffect

        calendarViewModel.processEvent(
            event = CalendarEvent.SearchFestivals(
                isLoading = calendarState.calendarInfo.calendarDays.isEmpty(),
                yearMonth = calendarState.currentYearMonth
            )
        )
    }

    LaunchedEffect(calendarState.hasError) {
        if (calendarState.hasError) {
            calendarViewModel.processEvent(
                event = CalendarEvent.SearchFestivals(
                    isLoading = calendarState.calendarInfo.calendarDays.isEmpty(),
                    yearMonth = calendarState.currentYearMonth
                )
            )
        }
    }

    CalendarScreen(
        calendarState = calendarState,
        drawerState = drawerState,
        onSelectYearMonth = { yearMonth ->
            calendarViewModel.processEvent(CalendarEvent.UpdateCurrentYearMonth(yearMonth))
        },
        onInterestFestivalChanged = { isInterest ->
            calendarViewModel.processEvent(CalendarEvent.UpdateInterest(isInterest))
        },
        onFestivalClick = { festivalId ->
            calendarViewModel.processEvent(CalendarEvent.ClickFestival(festivalId))
        },
        onCategoryFilterItemClick = { categoryFilter ->
            calendarViewModel.processEvent(CalendarEvent.ClickCategoryFilterItem(categoryFilter))
        },
        onStateFilterItemClick = { stateFilter ->
            calendarViewModel.processEvent(CalendarEvent.ClickStateFilterItem(stateFilter))
        },
        onRegionFilterItemClick = { regionFilter ->
            calendarViewModel.processEvent(CalendarEvent.ClickRegionFilterItem(regionFilter))
        },
        onAgeFilterItemClick = { ageFilter ->
            calendarViewModel.processEvent(CalendarEvent.ClickAgeFilterItem(ageFilter))
        },
        onFilterClear = {
            calendarViewModel.processEvent(CalendarEvent.ClearFilter)
        },
        onSearchFestival = {
            calendarViewModel.processEvent(
                event = CalendarEvent.SearchFestivals(
                    isLoading = true,
                    yearMonth = calendarState.currentYearMonth
                )
            )
        }
    )
}

@Composable
private fun CalendarScreen(
    calendarState: CalendarState,
    drawerState: DrawerState,
    onSelectYearMonth: (yearMonth: String) -> Unit,
    onInterestFestivalChanged: (isInterest: Boolean) -> Unit,
    onFestivalClick: (festivalId: Int) -> Unit,
    onCategoryFilterItemClick: (calendarFilter: CalendarFilter) -> Unit,
    onStateFilterItemClick: (calendarFilter: CalendarFilter) -> Unit,
    onRegionFilterItemClick: (calendarFilter: CalendarFilter) -> Unit,
    onAgeFilterItemClick: (calendarFilter: CalendarFilter) -> Unit,
    onFilterClear: () -> Unit,
    onSearchFestival: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(drawerState.isClosed) {
        if (drawerState.isClosed && !calendarState.isLoading && calendarState.isFilterChanged()) {
            onSearchFestival()
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            modifier = Modifier,
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    CalendarFilterDrawer(
                        isFilterSelected = calendarState.isFilterSelected(),
                        categoryFilters = calendarState.categoryFilters,
                        stateFilters = calendarState.stateFilters,
                        regionFilters = calendarState.regionFilters,
                        ageFilters = calendarState.ageFilters,
                        onCloseFilterClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onFilterClear = onFilterClear,
                        onCategoryFilterItemClick = onCategoryFilterItemClick,
                        onStateFilterItemClick = onStateFilterItemClick,
                        onRegionFilterItemClick = onRegionFilterItemClick,
                        onAgeFilterItemClick = onAgeFilterItemClick
                    )
                }
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Scaffold(
                    containerColor = Gray900,
                    topBar = {
                        CalendarTopBar(
                            isInterest = calendarState.isInterest,
                            currentMonth = calendarState.currentYearMonth,
                            yearMonthItems = calendarState.yearMonthItems,
                            onSelectYearMonth = onSelectYearMonth,
                            onInterestFestivalChanged = onInterestFestivalChanged
                        )
                    },
                    content = { padding ->
                        if (!calendarState.isLoading) {
                            Box {
                                CalendarContent(
                                    modifier = Modifier.padding(padding),
                                    calendarGridState = calendarState.calendarGridState,
                                    calendarInfo = calendarState.calendarInfo,
                                    currentMonth = calendarState.currentYearMonth,
                                    onFestivalClick = onFestivalClick
                                )

                                CalendarFilterButton(
                                    modifier = Modifier
                                        .padding(padding)
                                        .align(Alignment.BottomCenter),
                                    isFilterSelected = calendarState.isFilterSelected(),
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
    isInterest: Boolean,
    currentMonth: String,
    yearMonthItems: List<String>,
    onSelectYearMonth: (yearMonth: String) -> Unit,
    onInterestFestivalChanged: (isInterest: Boolean) -> Unit,
) {
    val isInterestFestivalChecked by rememberUpdatedState(newValue = isInterest)
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
                onCheckedChange = {
                    onInterestFestivalChanged(it)
                }
            )

            Text(
                modifier = Modifier.singleClick(hasRipple = false) {
                    onInterestFestivalChanged(!isInterestFestivalChecked)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CalendarContent(
    modifier: Modifier,
    calendarGridState: LazyGridState,
    calendarInfo: CalendarInfo,
    currentMonth: String,
    onFestivalClick: (festivalId: Int) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val gridState = rememberSaveable(saver = LazyGridState.Saver) { calendarGridState }
    var scrollOffset by remember { mutableStateOf(0f) }
    val weeks = remember { mutableListOf("일", "월", "화", "수", "목", "금", "토") }
    val festivalDayWithOffsetItems = remember(currentMonth) {
        mutableStateListOf<FestivalDayWithOffset>()
    }
    val onFestivalAdd: (FestivalDay, Offset) -> Unit = { festivalDay, offset ->
        val isFestivalDayIncluded = festivalDayWithOffsetItems.any {
            it.festivalDay.festivalId == festivalDay.festivalId &&
                it.festivalDay.weekRange == festivalDay.weekRange
        }
        if (!isFestivalDayIncluded) {
            festivalDayWithOffsetItems.add(
                FestivalDayWithOffset(
                    festivalDay = festivalDay,
                    offset = offset
                )
            )
        }
    }
    val calendar = Calendar.getInstance()
    val isCurrentYearMonth by rememberUpdatedState(
        newValue = calendar.year() == calendarInfo.year && calendar.month() == calendarInfo.month
    )

    Box {
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            LazyVerticalGrid(
                modifier = modifier,
                state = gridState,
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
                        currentMonth = currentMonth,
                        day = day.day,
                        festivalDays = day.festivalDays,
                        isSunday = index == 0,
                        isCurrentMonth = false,
                        onFestivalClick = onFestivalClick,
                        onStartOrSundayPositioned = onFestivalAdd
                    )
                }

                itemsIndexed(calendarInfo.calendarDays) { index, day ->
                    CalendarDayView(
                        currentMonth = currentMonth,
                        day = day.day,
                        festivalDays = day.festivalDays,
                        isSunday = index % 7 == 7 - calendarInfo.beforeMonthDayCount,
                        isSaturday = index % 7 == 7 - calendarInfo.beforeMonthDayCount - 1,
                        isToday = isCurrentYearMonth && calendarInfo.today == (index + 1),
                        onFestivalClick = onFestivalClick,
                        onStartOrSundayPositioned = onFestivalAdd,
                        onScrollOffsetChanged = {
                            scrollOffset = it
                        }
                    )
                }

                itemsIndexed(calendarInfo.afterCalendarDays) { index, day ->
                    CalendarDayView(
                        currentMonth = currentMonth,
                        day = day.day,
                        festivalDays = day.festivalDays,
                        isSunday = false,
                        isSaturday = index == calendarInfo.afterMonthDayCount - 1,
                        isCurrentMonth = false,
                        onFestivalClick = onFestivalClick,
                        onStartOrSundayPositioned = onFestivalAdd
                    )
                }
            }
        }

        festivalDayWithOffsetItems.forEach {
            FestivalNameView(
                festivalName = it.festivalDay.festivalName,
                festivalCountInWeek = it.festivalDay.festivalCountInWeek,
                dayWidth = (screenWidth / 7) - 6.dp,
                offset = it.offset.copy(y = it.offset.y + scrollOffset)
            )
        }
    }
}

@Composable
private fun FestivalNameView(
    festivalName: String,
    festivalCountInWeek: Int,
    dayWidth: Dp,
    offset: Offset
) {
    val x = with(LocalDensity.current) { offset.x.toDp() }
    val y = with(LocalDensity.current) { offset.y.toDp() }
    Text(
        modifier = Modifier
            .width(dayWidth * festivalCountInWeek)
            .padding(start = 4.dp, top = 1.dp)
            .offset(x = x, y = y),
        text = festivalName,
        style = TextStyle(
            fontFamily = Suit,
            fontWeight = FontWeight.W600,
            fontSize = 10.sp,
            lineHeight = 16.sp,
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        ),
        color = Gray800,
        maxLines = 1
    )
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
            onSelectYearMonth = {},
            onInterestFestivalChanged = {},
            onFestivalClick = {},
            onCategoryFilterItemClick = {},
            onStateFilterItemClick = {},
            onRegionFilterItemClick = {},
            onAgeFilterItemClick = {},
            onFilterClear = {},
            onSearchFestival = {}
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
            onSelectYearMonth = {},
            onInterestFestivalChanged = {},
            onFestivalClick = {},
            onCategoryFilterItemClick = {},
            onStateFilterItemClick = {},
            onRegionFilterItemClick = {},
            onAgeFilterItemClick = {},
            onFilterClear = {},
            onSearchFestival = {}
        )
    }
}
