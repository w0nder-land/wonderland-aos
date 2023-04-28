package com.imaec.feature.festival.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class FestivalViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val festivalId = savedStateHandle["festivalId"] ?: -1
}
//    : WonderViewModel<FestivalEvent, FestivalResult, FestivalState, FestivalEffect>(
//    FestivalState()
// ) {
//    override fun Flow<FestivalEvent>.toResults(): Flow<FestivalResult> = merge(
//        filterIsInstance<FestivalEvent>().toFestivalResult()
//    )
//
//    override fun FestivalResult.reduce(state: FestivalState): FestivalState = state
//
//    private fun Flow<FestivalEvent>.toFestivalResult(): Flow<FestivalResult> = mapLatest {
//        object : FestivalResult.
//    }
// }
