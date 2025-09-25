package com.example.atomicswap.domain.usecases

import com.example.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeController(
    private val settingsRepository: SettingsRepository,
) {
    private val _isDark = MutableStateFlow(settingsRepository.isDark())
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

    fun setDark(value: Boolean) {
        settingsRepository.setDark(value)
        _isDark.value = value
    }
}