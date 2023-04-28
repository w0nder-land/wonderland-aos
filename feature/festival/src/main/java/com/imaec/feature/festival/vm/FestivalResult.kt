package com.imaec.feature.festival.vm

import com.imaec.model.festival.FestivalDetailVo
import com.wonder.base.WonderResult

internal sealed interface FestivalResult : WonderResult {

    data class Festival(val festival: FestivalDetailVo) : FestivalResult
}
