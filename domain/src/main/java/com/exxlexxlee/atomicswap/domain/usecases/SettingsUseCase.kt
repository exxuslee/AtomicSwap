package com.exxlexxlee.atomicswap.domain.usecases

import android.util.Log
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

interface SettingsUseCase {
    fun selectedRoute(): String
    fun selectedRoute(route: String)

    val isTermsOfUseRead: StateFlow<Boolean>

    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

    fun badgeType(): Int?

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : SettingsUseCase {

        private val _isTermsOfUseRead = MutableStateFlow(isTermsOfUseRead())
        override val isTermsOfUseRead: StateFlow<Boolean> = _isTermsOfUseRead

        override fun selectedRoute() = settingsRepository.selectedRoute()

        override fun selectedRoute(route: String) = settingsRepository.selectedRoute(route)

        override fun isTermsOfUseRead(): Boolean {
            val isTermsOfUseRead = settingsRepository.isTermsOfUseRead()
            Log.d("SettingsUseCase", "isTermsOfUseRead $isTermsOfUseRead")
            return isTermsOfUseRead
        }

        override fun isTermsOfUseRead(ok: Boolean) {
            settingsRepository.isTermsOfUseRead(ok)
            _isTermsOfUseRead.value = ok
        }

        override fun badgeType() = if (_isTermsOfUseRead.value) null else 0


    }

}



