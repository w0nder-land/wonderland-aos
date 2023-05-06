package com.imaec.feature.calendar.filter

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wonder.component.theme.Body2
import com.wonder.component.theme.Caption1
import com.wonder.component.theme.Gray200
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Gray500
import com.wonder.component.theme.Gray600
import com.wonder.component.theme.Gray700
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.Heading2
import com.wonder.component.theme.Subtitle2
import com.wonder.component.theme.White
import com.wonder.component.theme.Wonder500
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.divider.HorizontalDivider
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@Composable
internal fun CalendarFilterDrawer(
    isFilterSelected: Boolean,
    selectedFilters: List<CalendarFilter>,
    categoryFilters: List<CalendarFilter>,
    stateFilters: List<CalendarFilter>,
    regionFilters: List<CalendarFilter>,
    ageFilters: List<CalendarFilter>,
    onCloseFilterClick: () -> Unit,
    onFilterClear: () -> Unit,
    onCategoryFilterItemClick: (calendarFilter: CalendarFilter) -> Unit,
    onStateFilterItemClick: (calendarFilter: CalendarFilter) -> Unit,
    onRegionFilterItemClick: (calendarFilter: CalendarFilter) -> Unit,
    onAgeFilterItemClick: (calendarFilter: CalendarFilter) -> Unit,
) {
    var isCategoryExpanded by remember { mutableStateOf(false) }
    var isStateExpanded by remember { mutableStateOf(false) }
    var isRegionExpanded by remember { mutableStateOf(false) }
    var isAgeExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(275.dp)
            .background(Gray800)
            .singleClick(hasRipple = false) { }
    ) {
        CalendarFilterTopView(
            isFilterSelected = isFilterSelected,
            selectedFilters = selectedFilters,
            onCloseFilterClick = onCloseFilterClick,
            onFilterClear = onFilterClear,
            onDeleteCategoryFilterClick = onCategoryFilterItemClick,
            onDeleteStateFilterClick = onStateFilterItemClick,
            onDeleteRegionFilterClick = onRegionFilterItemClick,
            onDeleteAgeFilterClick = onAgeFilterItemClick
        )

        LazyColumn {
            item {
                CalendarFilterItem(
                    title = "장르",
                    selectedFilter = categoryFilters
                        .filterNot { it.title == "전체" }.filter { it.isSelected },
                    isExpanded = isCategoryExpanded,
                    onFilterTitleClick = { isCategoryExpanded = !isCategoryExpanded },
                    content = {
                        Column(modifier = Modifier.padding(vertical = 14.dp)) {
                            categoryFilters.forEach { category ->
                                CalendarFilterItemView(
                                    title = category.title,
                                    count = category.count,
                                    isSelected = category.isSelected,
                                    onFilterItemClick = { onCategoryFilterItemClick(category) }
                                )
                            }
                        }
                    }
                )
            }

            item {
                CalendarFilterItem(
                    title = "진행상태",
                    selectedFilter = stateFilters
                        .filterNot { it.title == "전체" }.filter { it.isSelected },
                    isExpanded = isStateExpanded,
                    onFilterTitleClick = { isStateExpanded = !isStateExpanded },
                    content = {
                        Column(modifier = Modifier.padding(vertical = 14.dp)) {
                            stateFilters.forEach { state ->
                                CalendarFilterItemView(
                                    title = state.title,
                                    count = state.count,
                                    isSelected = state.isSelected,
                                    onFilterItemClick = { onStateFilterItemClick(state) }
                                )
                            }
                        }
                    }
                )
            }

            item {
                CalendarFilterItem(
                    title = "지역",
                    selectedFilter = regionFilters
                        .filterNot { it.title == "전체" }.filter { it.isSelected },
                    isExpanded = isRegionExpanded,
                    onFilterTitleClick = { isRegionExpanded = !isRegionExpanded },
                    content = {
                        Column(modifier = Modifier.padding(vertical = 14.dp)) {
                            regionFilters.chunked(2).forEach { chunkedRegions ->
                                Row {
                                    chunkedRegions.forEach { region ->
                                        CalendarFilterItemView(
                                            title = region.title,
                                            count = region.count,
                                            isSelected = region.isSelected,
                                            width = 137.dp,
                                            onFilterItemClick = { onRegionFilterItemClick(region) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }

            item {
                CalendarFilterItem(
                    title = "연령",
                    selectedFilter = ageFilters
                        .filterNot { it.title == "전체" }.filter { it.isSelected },
                    isExpanded = isAgeExpanded,
                    onFilterTitleClick = { isAgeExpanded = !isAgeExpanded },
                    content = {
                        Column(modifier = Modifier.padding(vertical = 14.dp)) {
                            ageFilters.forEach { age ->
                                CalendarFilterItemView(
                                    title = age.title,
                                    count = age.count,
                                    isSelected = age.isSelected,
                                    onFilterItemClick = { onAgeFilterItemClick(age) }
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
private fun CalendarFilterTopView(
    isFilterSelected: Boolean,
    selectedFilters: List<CalendarFilter>,
    onCloseFilterClick: () -> Unit,
    onFilterClear: () -> Unit,
    onDeleteCategoryFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteStateFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteRegionFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteAgeFilterClick: (filter: CalendarFilter) -> Unit,
) {
    Row(
        modifier = Modifier.padding(start = 20.dp, end = 16.dp, top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "필터",
            style = Heading2,
            color = White
        )

        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = "27397건",
            style = Body2,
            color = Gray200
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isFilterSelected) {
            Row(
                modifier = Modifier
                    .width(64.dp)
                    .height(26.dp)
                    .border(
                        width = 1.dp,
                        color = Gray600,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .singleClick(shape = RoundedCornerShape(4.dp)) { onFilterClear() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_retry),
                    tint = Gray300,
                    contentDescription = null
                )

                Text(
                    text = "초기화",
                    style = Caption1,
                    color = White
                )
            }
        }

        Icon(
            modifier = Modifier
                .padding(start = 10.dp)
                .size(32.dp)
                .singleClick(shape = CircleShape) { onCloseFilterClick() },
            painter = painterResource(id = R.drawable.ic_close_drawer),
            tint = White,
            contentDescription = null
        )
    }

    if (selectedFilters.isNotEmpty()) {
        CalendarSelectedFiltersFlow(
            selectedFilters = selectedFilters,
            onDeleteCategoryFilterClick = onDeleteCategoryFilterClick,
            onDeleteStateFilterClick = onDeleteStateFilterClick,
            onDeleteRegionFilterClick = onDeleteRegionFilterClick,
            onDeleteAgeFilterClick = onDeleteAgeFilterClick,
        )
    }

    HorizontalDivider(
        modifier = Modifier.padding(top = 16.dp),
        color = Gray600
    )
}

@Composable
private fun CalendarFilterItem(
    title: String,
    selectedFilter: List<CalendarFilter>,
    isExpanded: Boolean,
    onFilterTitleClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val iconRotate by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f
    )
    Column(modifier = Modifier.animateContentSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(
                    color = if (isExpanded) Gray700 else Gray800
                )
                .singleClick { onFilterTitleClick() }
                .padding(start = 20.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(end = 30.dp),
                text = title,
                style = Subtitle2,
                color = White
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedFilter.isNotEmpty()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = selectedFilter.joinToString(", ") { it.title },
                        style = Body2,
                        color = Gray200,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(iconRotate),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    tint = if (selectedFilter.isNotEmpty()) Wonder500 else Gray500,
                    contentDescription = null
                )
            }
        }

        if (isExpanded) {
            HorizontalDivider(color = Gray600)

            content()
        }
    }

    HorizontalDivider(color = Gray600)
}

@Preview
@Composable
private fun CalendarFilterDrawerPreview() {
    WonderTheme {
        CalendarFilterDrawer(
            isFilterSelected = false,
            selectedFilters = emptyList(),
            categoryFilters = emptyList(),
            stateFilters = emptyList(),
            regionFilters = emptyList(),
            ageFilters = emptyList(),
            onCloseFilterClick = {},
            onFilterClear = {},
            onCategoryFilterItemClick = {},
            onStateFilterItemClick = {},
            onRegionFilterItemClick = {},
            onAgeFilterItemClick = {}
        )
    }
}
