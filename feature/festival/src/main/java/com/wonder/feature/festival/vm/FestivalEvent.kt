package com.wonder.feature.festival.vm

import com.wonder.base.WonderEvent

internal sealed interface FestivalEvent : WonderEvent {

    object GetFestival : FestivalEvent
}
