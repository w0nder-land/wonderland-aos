package com.wonder.feature.festival.vm

import com.wonder.model.festival.FestivalDetailVo
import com.wonder.base.WonderState

internal data class FestivalState(
    override val isLoading: Boolean = true,
    override val hasError: Boolean = false,
    val festival: FestivalDetailVo? = null
) : WonderState
