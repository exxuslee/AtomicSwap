package com.exxlexxlee.atomicswap.domain.usecases

import android.util.Log
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {
    fun selectedRoute(): String
    fun selectedRoute(route: String)

    val isTermsOfUseRead: Flow<Boolean>

    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

    val isMainNetworkType: Flow<Boolean>
    fun isMainNetworkType(): Boolean
    fun isMainNetworkType(ok: Boolean)

    fun badgeType(): Int?

    val selectedFilterStateChronicle: Flow<FilterStateChronicle>
    fun selectedFilterStateChronicle(filterState: FilterStateChronicle)
    fun selectedFilterStateChronicle(): FilterStateChronicle

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : SettingsUseCase {
        override val selectedFilterStateChronicle = settingsRepository.selectedFilterStateChronicle

        override fun selectedRoute() = settingsRepository.selectedRoute()

        override fun selectedRoute(route: String) = settingsRepository.selectedRoute(route)

        override val isTermsOfUseRead = settingsRepository.isTermsOfUseRead

        override fun isTermsOfUseRead(): Boolean {
            val isTermsOfUseRead = settingsRepository.isTermsOfUseRead()
            Log.d("SettingsUseCase", "isTermsOfUseRead $isTermsOfUseRead")
            return isTermsOfUseRead
        }

        override fun isTermsOfUseRead(ok: Boolean) {
            settingsRepository.isTermsOfUseRead(ok)
        }

        override fun badgeType() = if (isTermsOfUseRead()) null else 0

        override fun selectedFilterStateChronicle(filterState: FilterStateChronicle) =
            settingsRepository.selectedFilterStateChronicle(filterState)

        override fun selectedFilterStateChronicle() =
            settingsRepository.selectedFilterStateChronicle()

        override val isMainNetworkType = settingsRepository.isMainNetworkType

        override fun isMainNetworkType() = settingsRepository.isMainNetworkType()

        override fun isMainNetworkType(ok: Boolean) {
            settingsRepository.isMainNetworkType(ok)
        }


    }

}



