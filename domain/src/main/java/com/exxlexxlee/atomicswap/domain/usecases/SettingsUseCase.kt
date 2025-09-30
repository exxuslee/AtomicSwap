package com.exxlexxlee.atomicswap.domain.usecases

import android.util.Log
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

interface SettingsUseCase {
    fun selectedRoute(): String
    fun selectedRoute(route: String)

    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : SettingsUseCase {

        override fun selectedRoute() = settingsRepository.selectedRoute()

        override fun selectedRoute(route: String) = settingsRepository.selectedRoute(route)

        override fun isTermsOfUseRead(): Boolean {
            val isTermsOfUseRead = settingsRepository.isTermsOfUseRead()
            Log.d("SettingsUseCase", "isTermsOfUseRead $isTermsOfUseRead")
            return isTermsOfUseRead
        }

        override fun isTermsOfUseRead(ok: Boolean) = settingsRepository.isTermsOfUseRead(ok)

    }

}



