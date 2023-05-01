package com.wonder.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Event가 발생하게 되면 Result를 생성
 * Result에 따라 toStates()와 reduce()에서 새로운 상태가 생성 되거나
 * toEffects()에서 새로운 동작이 생성된다.
 */
abstract class WonderViewModel
<E : WonderEvent, R : WonderResult, S : WonderState, SE : WonderEffect>(
    initialState: S
) : ViewModel() {
    val states: StateFlow<S>
    val effects: Flow<SE>
    private val events = MutableSharedFlow<E>()

    init {
        events
            .share()
            .toResults()
            .share()
            .also { result ->
                states = result.toStates(initialState)
                    .stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.Eagerly,
                        initialValue = initialState
                    )
                effects = result.toEffects().share()
            }
    }

    fun processEvent(event: E) {
        viewModelScope.launch {
            events.emit(event)
        }
    }

    protected abstract fun Flow<E>.toResults(): Flow<R>
    protected abstract fun R.reduce(state: S): S
    protected open fun Flow<R>.toEffects(): Flow<SE> = emptyFlow()

    private fun Flow<R>.toStates(initialState: S): Flow<S> {
        return scan(initialState) { state, event -> event.reduce(state) }
    }

    private fun <T> Flow<T>.share(): Flow<T> {
        return shareIn(scope = viewModelScope, started = SharingStarted.Eagerly)
    }
}

interface WonderEvent
interface WonderResult
interface WonderState {
    val isLoading: Boolean
    val hasError: Boolean
}
interface WonderEffect
