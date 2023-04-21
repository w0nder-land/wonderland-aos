package com.wonder.feature.splash.vm

import com.wonder.base.WonderEvent

internal sealed interface SplashEvent : WonderEvent {

    object CheckServer : SplashEvent
}
