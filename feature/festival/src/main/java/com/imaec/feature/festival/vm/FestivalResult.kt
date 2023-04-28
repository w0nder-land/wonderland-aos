package com.imaec.feature.festival.vm

import com.wonder.base.WonderResult
import com.wonder.domain.model.festival.FestivalDetail

internal sealed interface FestivalResult : WonderResult {

    data class Festival(val festival: FestivalDetail) : FestivalResult
}
