package com.wonder.wonderland.presentation.calendar.vm

import androidx.compose.foundation.lazy.grid.LazyGridState
import com.wonder.base.WonderState
import com.wonder.wonderland.presentation.calendar.filter.CalendarFilter
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo
import java.util.function.Predicate.isEqual

internal data class CalendarState(
    override val isLoading: Boolean = true,
    override val hasError: Boolean = false,
    val calendarGridState: LazyGridState = LazyGridState(),
    val currentYearMonth: String = "",
    val isInterest: Boolean = false,
    val yearMonthItems: List<String> = emptyList(),
    val calendarInfo: CalendarInfo = CalendarInfo(),
    val categoryFilters: List<CalendarFilter> = emptyList(),
    val stateFilters: List<CalendarFilter> = emptyList(),
    val regionFilters: List<CalendarFilter> = emptyList(),
    val ageFilters: List<CalendarFilter> = emptyList(),
    val selectedCategoryFilters: List<CalendarFilter> = emptyList(),
    val selectedStateFilters: List<CalendarFilter> = emptyList(),
    val selectedRegionFilters: List<CalendarFilter> = emptyList(),
    val selectedAgeFilters: List<CalendarFilter> = emptyList(),
) : WonderState

internal fun CalendarState.isFilterChanged(): Boolean =
    !isEqual(categoryFilters, selectedCategoryFilters) ||
        !isEqual(stateFilters, selectedStateFilters) ||
        !isEqual(regionFilters, selectedRegionFilters) ||
        !isEqual(ageFilters, selectedAgeFilters)

private fun isEqual(first: List<CalendarFilter>, second: List<CalendarFilter>): Boolean {
    if (first.size != second.size) {
        return false
    }
    return first.zip(second).all { (filter, selectedFilter) ->
        filter.isSelected == selectedFilter.isSelected
    }
}

internal fun CalendarState.isFilterSelected(): Boolean =
    categoryFilters.filterNot { it.title == "전체" }.any { it.isSelected } ||
        stateFilters.filterNot { it.title == "전체" }.any { it.isSelected } ||
        regionFilters.filterNot { it.title == "전체" }.any { it.isSelected } ||
        ageFilters.filterNot { it.title == "전체" }.any { it.isSelected }

internal fun CalendarState.getSelectedFilters() = mutableListOf<CalendarFilter>().apply {
    addAll(categoryFilters.filterNot { it.title == "전체" }.filter { it.isSelected })
    addAll(stateFilters.filterNot { it.title == "전체" }.filter { it.isSelected })
    addAll(regionFilters.filterNot { it.title == "전체" }.filter { it.isSelected })
    addAll(ageFilters.filterNot { it.title == "전체" }.filter { it.isSelected })
}

internal fun CalendarState.getSelectedConfirmedFilters() = mutableListOf<CalendarFilter>().apply {
    addAll(selectedCategoryFilters.filterNot { it.title == "전체" }.filter { it.isSelected })
    addAll(selectedStateFilters.filterNot { it.title == "전체" }.filter { it.isSelected })
    addAll(selectedRegionFilters.filterNot { it.title == "전체" }.filter { it.isSelected })
    addAll(selectedAgeFilters.filterNot { it.title == "전체" }.filter { it.isSelected })
}
