package com.imaec.feature.festival.vm

import com.wonder.base.WonderState

internal data class FestivalState(
    override val isLoading: Boolean = false,
    override val hasError: Boolean = false
) : WonderState
