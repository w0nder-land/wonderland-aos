package com.wonder.feature.festival.vm

import com.wonder.model.festival.FestivalDetailVo
import com.wonder.base.WonderResult

internal sealed interface FestivalResult : WonderResult {

    data class Festival(val festival: FestivalDetailVo) : FestivalResult

    object Error : FestivalResult
}
