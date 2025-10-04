package com.exxlexxlee.atomicswap.data.repository

import android.content.SharedPreferences
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import androidx.core.content.edit
import com.exxlexxlee.atomicswap.core.network.ConnectionManager
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

class SettingsRepositoryImpl(
    private val prefs: SharedPreferences,
    connectionManager: ConnectionManager,
) : SettingsRepository {

    override val connectionState = connectionManager.connectionState

    override fun isDark(): Boolean {
        return prefs.getBoolean("isDark", false)
    }
    override fun isDark(value: Boolean) {
        prefs.edit { putBoolean("isDark", value) }
    }

    override fun selectedRoute(): String {
        return prefs.getString("selectedRoute", "maker") ?: "maker"
    }
    override fun selectedRoute(route: String) {
        prefs.edit { putString("selectedRoute", route) }
    }

    private val _isTermsOfUseRead = MutableStateFlow(isTermsOfUseRead())
    override val isTermsOfUseRead: StateFlow<Boolean> = _isTermsOfUseRead

    override fun isTermsOfUseRead(): Boolean {
        return prefs.getBoolean("isTermsOfUseRead", false)
    }
    override fun isTermsOfUseRead(ok: Boolean) {
        prefs.edit { putBoolean("isTermsOfUseRead", ok) }
        _isTermsOfUseRead.value = ok
    }

    private val _selectedAggregator = MutableStateFlow(selectedAggregator())
    override val selectedAggregator: StateFlow<SupportedAggregators> = _selectedAggregator
    override fun selectedAggregator(): SupportedAggregators {
        val label = prefs.getString("selectedAggregator", "") ?: ""
        return SupportedAggregators.fromLabel(label)
    }
    override fun selectedAggregator(label: String) {
        prefs.edit { putString("selectedAggregator", label) }
        _selectedAggregator.value = SupportedAggregators.fromLabel(label)
    }

    private val _selectedFilterStateChronicle = MutableStateFlow(selectedFilterStateChronicle())
    override val selectedFilterStateChronicle: StateFlow<FilterStateChronicle> = _selectedFilterStateChronicle
    override fun selectedFilterStateChronicle(): FilterStateChronicle {
        val pos = prefs.getInt("filterStateChronicle", 1)
        return FilterStateChronicle.fromPos(pos)
    }
    override fun selectedFilterStateChronicle(filterStateChronicle: FilterStateChronicle) {
        _selectedFilterStateChronicle.value = filterStateChronicle
        prefs.edit { putInt("filterStateChronicle", filterStateChronicle.pos) }
    }
}