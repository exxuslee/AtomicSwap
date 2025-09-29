package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

interface SettingsUseCase {
    fun selectedRoute(): String
    fun selectedRoute(route: String)

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : SettingsUseCase {

        override fun selectedRoute() = settingsRepository.selectedRoute()


        override fun selectedRoute(route: String) = settingsRepository.selectedRoute(route)

    }

}



