package com.wonder.feature.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wonder.component.theme.Caption2
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray900
import com.wonder.component.theme.Subtitle1
import com.wonder.component.theme.White
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.loading.LoadingView
import com.wonder.component.ui.singleClick
import com.wonder.component.ui.switch.WonderSwitch
import com.wonder.resource.R
import com.wonder.feature.calendar.bottomsheet.SelectMonthBottomSheetDialog
import com.wonder.feature.calendar.filter.CalendarFilter
import com.wonder.feature.calendar.filter.CalendarFilterButton
import com.wonder.feature.calendar.filter.CalendarFilterDrawer
import com.wonder.feature.calendar.filter.CalendarSelectedFiltersRow
import com.wonder.feature.calendar.model.CalendarDayInfoVo
import com.wonder.feature.calendar.model.CalendarInfoVo
import com.wonder.feature.calendar.vm.CalendarEffect
import com.wonder.feature.calendar.vm.CalendarEvent
import com.wonder.feature.calendar.vm.CalendarState
import com.wonder.feature.calendar.vm.CalendarViewModel
import com.wonder.feature.calendar.vm.getSelectedConfirmedFilters
import com.wonder.feature.calendar.vm.getSelectedFilters
import com.wonder.feature.calendar.vm.isFilterChanged
import com.wonder.feature.calendar.vm.isFilterSelected
import kotlinx.coroutines.launch

@Composable
internal fun CalendarView(
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
                isLoading = true,
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
                        selectedFilters = calendarState.getSelectedFilters(),
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
                        Box {
                            CalendarContent(
                                modifier = Modifier.padding(padding),
                                calendarListState = calendarState.calendarListState,
                                calendarInfo = calendarState.calendarInfo,
                                selectedFilters = calendarState.getSelectedConfirmedFilters(),
                                onDeleteCategoryFilterClick = {
                                    onCategoryFilterItemClick(it)
                                    onSearchFestival()
                                },
                                onDeleteStateFilterClick = {
                                    onStateFilterItemClick(it)
                                    onSearchFestival()
                                },
                                onDeleteRegionFilterClick = {
                                    onRegionFilterItemClick(it)
                                    onSearchFestival()
                                },
                                onDeleteAgeFilterClick = {
                                    onAgeFilterItemClick(it)
                                    onSearchFestival()
                                },
                                onFilterClear = {
                                    onFilterClear()
                                    onSearchFestival()
                                },
                                onFestivalClick = onFestivalClick
                            )

                            if (!calendarState.isLoading &&
                                calendarState.calendarInfo.calendarDays.isNotEmpty()
                            ) {
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

                            if (calendarState.isLoading) {
                                LoadingView()
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

@Composable
private fun CalendarContent(
    modifier: Modifier,
    calendarListState: LazyListState,
    calendarInfo: CalendarInfoVo,
    selectedFilters: List<CalendarFilter>,
    onDeleteCategoryFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteStateFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteRegionFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteAgeFilterClick: (filter: CalendarFilter) -> Unit,
    onFilterClear: () -> Unit,
    onFestivalClick: (festivalId: Int) -> Unit,
) {
    Box {
        Column(modifier = modifier) {
            if (selectedFilters.isNotEmpty()) {
                CalendarSelectedFiltersRow(
                    selectedFilters = selectedFilters,
                    onDeleteCategoryFilterClick = onDeleteCategoryFilterClick,
                    onDeleteStateFilterClick = onDeleteStateFilterClick,
                    onDeleteRegionFilterClick = onDeleteRegionFilterClick,
                    onDeleteAgeFilterClick = onDeleteAgeFilterClick,
                    onFilterClear = onFilterClear
                )
            }

            CalendarColumn(
                calendarListState = calendarListState,
                calendarInfo = calendarInfo,
                onFestivalClick = onFestivalClick
            )
        }
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    val calendarDays = mutableListOf<CalendarDayInfoVo>().apply {
        repeat(30) {
            add(CalendarDayInfoVo(year = 2023, month = 5, day = it + 1))
        }
    }
    val beforeCalendarDays = mutableListOf<CalendarDayInfoVo>().apply {
        repeat(6) {
            add(CalendarDayInfoVo(year = 2023, month = 5, day = it + 21))
        }
    }
    val afterCalendarDays = mutableListOf<CalendarDayInfoVo>().apply {
        repeat(6) {
            add(CalendarDayInfoVo(year = 2023, month = 5, day = it + 1))
        }
    }
    WonderTheme {
        CalendarScreen(
            calendarState = CalendarState(
                isLoading = false,
                currentYearMonth = "2023년 4월",
                calendarInfo = CalendarInfoVo(
                    today = 22,
                    calendarDays = beforeCalendarDays + calendarDays + afterCalendarDays
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
    val calendarDays = mutableListOf<CalendarDayInfoVo>().apply {
        repeat(30) {
            add(CalendarDayInfoVo(year = 2023, month = 5, day = it + 1))
        }
    }
    val beforeCalendarDays = mutableListOf<CalendarDayInfoVo>().apply {
        repeat(6) {
            add(CalendarDayInfoVo(year = 2023, month = 5, day = it + 21))
        }
    }
    val afterCalendarDays = mutableListOf<CalendarDayInfoVo>().apply {
        repeat(6) {
            add(CalendarDayInfoVo(year = 2023, month = 5, day = it + 1))
        }
    }
    WonderTheme {
        CalendarScreen(
            calendarState = CalendarState(
                isLoading = false,
                currentYearMonth = "2023년 4월",
                calendarInfo = CalendarInfoVo(
                    today = 22,
                    calendarDays = beforeCalendarDays + calendarDays + afterCalendarDays
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
