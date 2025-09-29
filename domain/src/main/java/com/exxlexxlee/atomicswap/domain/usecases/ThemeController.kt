package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeController(
    private val settingsRepository: SettingsRepository,
) {
    private val _isDark = MutableStateFlow(settingsRepository.isDark())
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

    fun setDark(value: Boolean) {
        settingsRepository.isDark(value)
        _isDark.value = value
    }
}