package com.imaec.feature.festival.vm

import androidx.lifecycle.SavedStateHandle
import com.imaec.model.festival.toVo
import com.wonder.base.WonderViewModel
import com.wonder.domain.usecase.festival.GetFestivalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

@HiltViewModel
internal class FestivalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFestivalUseCase: GetFestivalUseCase,
) : WonderViewModel<FestivalEvent, FestivalResult, FestivalState, FestivalEffect>(
    FestivalState()
) {
    val festivalId = savedStateHandle["festivalId"] ?: -1

    override fun Flow<FestivalEvent>.toResults(): Flow<FestivalResult> = merge(
        filterIsInstance<FestivalEvent.GetFestival>().toGetFestivalResult()
    )

    override fun FestivalResult.reduce(state: FestivalState): FestivalState {
        return when (this) {
            is FestivalResult.Festival -> {
                state.copy(
                    isLoading = false,
                    festival = festival
                )
            }
        }
    }

    private fun Flow<FestivalEvent.GetFestival>.toGetFestivalResult(): Flow<FestivalResult> =
        mapLatest {
            val festivalDetail = getFestivalUseCase(festivalId).toVo()
            FestivalResult.Festival(festivalDetail)
        }
}
