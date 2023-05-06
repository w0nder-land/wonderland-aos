package com.imaec.feature.calendar.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.imaec.model.festival.FestivalAgeType
import com.imaec.model.festival.FestivalCategoryType
import com.imaec.model.festival.FestivalRegionType
import com.imaec.model.festival.FestivalStateType
import com.wonder.component.theme.Caption1
import com.wonder.component.theme.Gray300
import com.wonder.component.theme.Gray50
import com.wonder.component.theme.Gray500
import com.wonder.component.theme.Gray800
import com.wonder.component.theme.WonderTheme
import com.wonder.component.ui.singleClick
import com.wonder.resource.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CalendarSelectedFiltersFlow(
    selectedFilters: List<CalendarFilter>,
    onDeleteCategoryFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteStateFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteRegionFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteAgeFilterClick: (filter: CalendarFilter) -> Unit,
) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        selectedFilters.forEach { filter ->
            CalendarSelectedFilterView(
                modifier = Modifier.padding(top = 12.dp),
                filter = filter,
                onDeleteFilterClick = {
                    when {
                        FestivalCategoryType
                            .values()
                            .find {
                                it.name == filter.code
                            } != null -> {
                            onDeleteCategoryFilterClick(filter)
                        }

                        FestivalStateType
                            .values()
                            .find {
                                it.name == filter.code
                            } != null -> {
                            onDeleteStateFilterClick(filter)
                        }

                        FestivalRegionType
                            .values()
                            .find {
                                it.name == filter.code
                            } != null -> {
                            onDeleteRegionFilterClick(filter)
                        }

                        FestivalAgeType
                            .values()
                            .find {
                                it.name == filter.code
                            } != null -> {
                            onDeleteAgeFilterClick(filter)
                        }
                    }
                }
            )
        }
    }
}

@Composable
internal fun CalendarSelectedFiltersRow(
    selectedFilters: List<CalendarFilter>,
    onDeleteCategoryFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteStateFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteRegionFilterClick: (filter: CalendarFilter) -> Unit,
    onDeleteAgeFilterClick: (filter: CalendarFilter) -> Unit,
    onFilterClear: () -> Unit,
) {
    Box {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(Gray800),
            contentPadding = PaddingValues(start = 16.dp, end = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(selectedFilters) { filter ->
                CalendarSelectedFilterView(
                    filter = filter,
                    onDeleteFilterClick = {
                        when {
                            FestivalCategoryType
                                .values()
                                .find {
                                    it.name == filter.code
                                } != null -> {
                                onDeleteCategoryFilterClick(filter)
                            }

                            FestivalStateType
                                .values()
                                .find {
                                    it.name == filter.code
                                } != null -> {
                                onDeleteStateFilterClick(filter)
                            }

                            FestivalRegionType
                                .values()
                                .find {
                                    it.name == filter.code
                                } != null -> {
                                onDeleteRegionFilterClick(filter)
                            }

                            FestivalAgeType
                                .values()
                                .find {
                                    it.name == filter.code
                                } != null -> {
                                onDeleteAgeFilterClick(filter)
                            }
                        }
                    }
                )
            }
        }

        val colors = listOf(
            Gray800.copy(alpha = 0f),
            Gray800.copy(alpha = 1f),
            Gray800.copy(alpha = 1f),
            Gray800.copy(alpha = 1f),
            Gray800.copy(alpha = 1f),
            Gray800.copy(alpha = 1f)
        )
        Box(
            modifier = Modifier
                .size(width = 50.dp, height = 44.dp)
                .background(brush = Brush.horizontalGradient(colors = colors))
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.Center)
                    .singleClick(shape = CircleShape) { onFilterClear() },
                painter = painterResource(id = R.drawable.ic_retry),
                tint = Gray300,
                contentDescription = null
            )
        }
    }
}

@Composable
fun CalendarSelectedFilterView(
    modifier: Modifier = Modifier,
    filter: CalendarFilter,
    onDeleteFilterClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(26.dp)
            .background(
                color = Gray500,
                shape = CircleShape
            )
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = filter.title,
            style = Caption1,
            color = Gray50
        )

        Icon(
            modifier = Modifier
                .size(12.dp)
                .singleClick(shape = CircleShape) { onDeleteFilterClick() },
            painter = painterResource(id = R.drawable.ic_close),
            tint = Gray300,
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun CalendarSelectedFiltersFlowPreview() {
    WonderTheme {
        CalendarSelectedFiltersFlow(
            selectedFilters = emptyList(),
            onDeleteCategoryFilterClick = {},
            onDeleteStateFilterClick = {},
            onDeleteRegionFilterClick = {},
            onDeleteAgeFilterClick = {},
        )
    }
}

@Preview
@Composable
private fun CalendarSelectedFiltersRowPreview() {
    WonderTheme {
        CalendarSelectedFiltersRow(
            selectedFilters = emptyList(),
            onDeleteCategoryFilterClick = {},
            onDeleteStateFilterClick = {},
            onDeleteRegionFilterClick = {},
            onDeleteAgeFilterClick = {},
            onFilterClear = {}
        )
    }
}
