package com.imaec.feature.festival.vm

import com.wonder.base.WonderState
import com.wonder.domain.model.festival.FestivalDetail

internal data class FestivalState(
    override val isLoading: Boolean = true,
    override val hasError: Boolean = false,
    val festival: FestivalDetail? = null
) : WonderState
