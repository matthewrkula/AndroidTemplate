package com.mattkula.template.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ControllerState

abstract class Controller<
    CState : ControllerState,
    > : ViewModel() {

    protected abstract val initialState: CState

    val controllerScope get() = viewModelScope

    val state get() = _state.value
    val stateFlow: StateFlow<CState> get() = _state
    private val _state by lazy { MutableStateFlow(initialState) }

    fun updateState(state: CState.() -> CState) {
        val newState = state(_state.value)
        _state.value = newState
    }
}
