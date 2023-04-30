package com.wonder.wonderland.presentation.calendar.vm

import com.wonder.base.WonderEvent
import com.wonder.wonderland.presentation.calendar.filter.CalendarFilter

internal sealed interface CalendarEvent : WonderEvent {

    data class Loading(val isLoading: Boolean) : CalendarEvent

    object GetCurrentYearMonth : CalendarEvent

    data class SearchFestivals(
        val isLoading: Boolean,
        val yearMonth: String
    ) : CalendarEvent

    data class UpdateCurrentYearMonth(val yearMonth: String) : CalendarEvent

    data class UpdateInterest(val isInterest: Boolean) : CalendarEvent

    data class ClickFestival(val festivalId: Int) : CalendarEvent

    data class ClickCategoryFilterItem(val categoryFilter: CalendarFilter) : CalendarEvent

    data class ClickStateFilterItem(val stateFilter: CalendarFilter) : CalendarEvent

    data class ClickRegionFilterItem(val regionFilter: CalendarFilter) : CalendarEvent

    data class ClickAgeFilterItem(val ageFilter: CalendarFilter) : CalendarEvent

    object ClearFilter : CalendarEvent
}
