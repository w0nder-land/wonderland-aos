package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderResult
import com.wonder.wonderland.presentation.calendar.filter.CalendarFilter
import com.wonder.wonderland.presentation.calendar.model.CalendarInfo

internal sealed interface CalendarResult : WonderResult {

    data class Loading(val isLoading: Boolean) : CalendarResult

    data class CurrentYearMonth(
        val currentYearMonth: String,
        val yearMonthItems: List<String>
    ) : CalendarResult

    data class CurrentCalendar(
        val calendarInfo: CalendarInfo,
        val categoryFilters: List<CalendarFilter>,
        val stateFilters: List<CalendarFilter>,
        val regionFilters: List<CalendarFilter>,
        val ageFilters: List<CalendarFilter>,
        val selectedCategoryFilters: List<CalendarFilter>,
        val selectedStateFilters: List<CalendarFilter>,
        val selectedRegionFilters: List<CalendarFilter>,
        val selectedAgeFilters: List<CalendarFilter>
    ) : CalendarResult

    data class UpdateYearMonth(val currentYearMonth: String) : CalendarResult

    data class ClickFestival(val festivalId: Int) : CalendarResult

    data class ClickCategoryFilterItem(val categoryFilters: List<CalendarFilter>) : CalendarResult

    data class ClickStateFilterItem(val stateFilters: List<CalendarFilter>) : CalendarResult

    data class ClickRegionFilterItem(val regionFilters: List<CalendarFilter>) : CalendarResult

    data class ClickAgeFilterItem(val ageFilters: List<CalendarFilter>) : CalendarResult

    data class ClearFilter(
        val categoryFilters: List<CalendarFilter>,
        val stateFilters: List<CalendarFilter>,
        val regionFilters: List<CalendarFilter>,
        val ageFilters: List<CalendarFilter>
    ) : CalendarResult

    data class Error(
        val categoryFilters: List<CalendarFilter>,
        val stateFilters: List<CalendarFilter>,
        val regionFilters: List<CalendarFilter>,
        val ageFilters: List<CalendarFilter>
    ) : CalendarResult
}
