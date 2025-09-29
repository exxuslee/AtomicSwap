package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ThemeController {
    val isDark: StateFlow<Boolean>

    fun setDark(value: Boolean)

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : ThemeController {
        private val _isDark = MutableStateFlow(settingsRepository.isDark())
        override val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

        override fun setDark(value: Boolean) {
            settingsRepository.isDark(value)
            _isDark.value = value
        }
    }
}