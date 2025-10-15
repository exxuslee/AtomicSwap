package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.core.network.ConnectionManager
import com.exxlexxlee.atomicswap.domain.model.FilterStateBook
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    val connectionState: Flow<ConnectionManager.ConnectionState>
    fun isDark(): Boolean
    fun isDark(value: Boolean)
    fun selectedRoute(): String
    fun selectedRoute(route: String)

    val isTermsOfUseRead: Flow<Boolean>
    fun isTermsOfUseRead(): Boolean
    fun isTermsOfUseRead(ok: Boolean)

    val isMainNetworkType: Flow<Boolean>
    fun isMainNetworkType(): Boolean
    fun isMainNetworkType(ok: Boolean)

    val selectedAggregator: StateFlow<SupportedAggregators>
    fun selectedAggregator(): SupportedAggregators
    fun selectedAggregator(label: String)

    val selectedFilterStateChronicle: Flow<FilterStateChronicle>
    fun selectedFilterStateChronicle(): FilterStateChronicle
    fun selectedFilterStateChronicle(filterStateChronicle: FilterStateChronicle)

    val selectedFilterStateBook: Flow<FilterStateBook>
    fun selectedFilterStateBook(): FilterStateBook
    fun selectedFilterStateBook(filterStateBook: FilterStateBook)
}
