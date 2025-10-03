package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {
    fun isDark(): Boolean
    fun isDark(value: Boolean)
    fun selectedRoute(): String
    fun selectedRoute(route: String)

    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

    val selectedAggregator: StateFlow<SupportedAggregators>
    fun selectedAggregator(): SupportedAggregators
    fun selectedAggregator(label: String)

    val selectedFilterStateChronicle: Flow<FilterStateChronicle>
    fun selectedFilterStateChronicle(): FilterStateChronicle
    fun selectedFilterStateChronicle(filterStateChronicle: FilterStateChronicle)
}
