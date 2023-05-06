package com.imaec.feature.calendar.vm

import com.wonder.base.WonderEffect

internal sealed interface CalendarEffect : WonderEffect {

    data class MoveFestival(val festivalId: Int) : CalendarEffect
}
