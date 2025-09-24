package com.example.atomicswap.feature.settings

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeController(initialDark: Boolean = false) {
    private val _isDark = MutableStateFlow(initialDark)
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

    fun toggle() {
        _isDark.value = !_isDark.value
    }

    fun setDark(value: Boolean) {
        _isDark.value = value
    }
}
