package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun isDark(): Boolean
    fun isDark(value: Boolean)
    fun selectedRoute(): String
    fun selectedRoute(route: String)

    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)
    fun selectedAggregator(): String
    fun selectedAggregator(label: String)

    val selectedFilterStateChronicle: Flow<FilterStateChronicle>
    fun selectedFilterStateChronicle(): FilterStateChronicle
    fun selectedFilterStateChronicle(filterStateChronicle: FilterStateChronicle)
}
