package com.wonder.component.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

object ClickableCheckObject {

    private val throttledState = MutableStateFlow { }

    fun throttledFirstClicks(onClick: () -> Unit) {
        throttledState.value = onClick
    }

    init {
        CoroutineScope(Dispatchers.Main).launch {
            throttledState
                .throttleFirst(500)
                .collect { onClick: () -> Unit ->
                    onClick.invoke()
                }
        }
    }

    // throttle first for coroutine
    private fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
        require(periodMillis > 0) { "period should be positive" }
        return flow {
            var lastTime = 0L
            collect { value ->
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastTime >= periodMillis) {
                    lastTime = currentTime
                    emit(value)
                }
            }
        }
    }
}
