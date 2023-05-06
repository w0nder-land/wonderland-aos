package com.imaec.feature.calendar.vm

import com.wonder.base.WonderResult
import com.imaec.feature.calendar.filter.CalendarFilter
import com.imaec.feature.calendar.model.CalendarInfoVo

internal sealed interface CalendarResult : WonderResult {

    data class Loading(val isLoading: Boolean) : CalendarResult

    data class CurrentYearMonth(
        val currentYearMonth: String,
        val yearMonthItems: List<String>
    ) : CalendarResult

    data class CurrentCalendar(
        val isLoading: Boolean,
        val calendarInfo: CalendarInfoVo?,
        val categoryFilters: List<CalendarFilter> = emptyList(),
        val stateFilters: List<CalendarFilter> = emptyList(),
        val regionFilters: List<CalendarFilter> = emptyList(),
        val ageFilters: List<CalendarFilter> = emptyList(),
        val selectedCategoryFilters: List<CalendarFilter> = emptyList(),
        val selectedStateFilters: List<CalendarFilter> = emptyList(),
        val selectedRegionFilters: List<CalendarFilter> = emptyList(),
        val selectedAgeFilters: List<CalendarFilter> = emptyList()
    ) : CalendarResult

    data class UpdateYearMonth(val currentYearMonth: String) : CalendarResult

    data class UpdateInterest(val isInterest: Boolean) : CalendarResult

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
