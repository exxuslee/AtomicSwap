package com.example.atomicswap.domain.usecases

import com.example.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class SettingsUseCase(
    private val settingsRepository: SettingsRepository,
) {
    fun selectedRoute() = settingsRepository.selectedRoute()

    fun selectedRoute(route: String) = settingsRepository.selectedRoute(route)

}



