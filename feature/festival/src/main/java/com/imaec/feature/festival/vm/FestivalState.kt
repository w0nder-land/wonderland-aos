package com.imaec.feature.festival.vm

import com.imaec.model.festival.FestivalDetailVo
import com.wonder.base.WonderState

internal data class FestivalState(
    override val isLoading: Boolean = true,
    override val hasError: Boolean = false,
    val festival: FestivalDetailVo? = null
) : WonderState
