package com.exxlexxlee.atomicswap.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.exxlexxlee.atomicswap.core.network.ConnectionManager
import com.exxlexxlee.atomicswap.domain.model.FilterStateBook
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepositoryImpl(
    private val prefs: SharedPreferences,
    connectionManager: ConnectionManager,
) : SettingsRepository {
    private companion object {
        const val KEY_IS_DARK = "isDark"
        const val KEY_SELECTED_ROUTE = "selectedRoute"
        const val KEY_TERMS_OF_USE_READ = "isTermsOfUseRead"
        const val KEY_MAIN_NETWORK_TYPE = "isMainNetworkType"
        const val KEY_SELECTED_AGGREGATOR = "selectedAggregator"
        const val KEY_FILTER_STATE_CHRONICLE = "filterStateChronicle"
        const val KEY_FILTER_STATE_BOOK = "filterStateBook"
        const val DEFAULT_ROUTE = "book/main"
    }

    override val connectionState = connectionManager.connectionState

    override fun isDark(): Boolean = prefs.getBoolean(KEY_IS_DARK, false)
    override fun isDark(value: Boolean) = prefs.edit { putBoolean(KEY_IS_DARK, value) }


    override fun selectedRoute(): String =
        prefs.getString(KEY_SELECTED_ROUTE, DEFAULT_ROUTE) ?: DEFAULT_ROUTE
    override fun selectedRoute(route: String) = prefs.edit { putString(KEY_SELECTED_ROUTE, route) }


    private val _isTermsOfUseRead = MutableStateFlow(isTermsOfUseRead())
    override val isTermsOfUseRead: StateFlow<Boolean> = _isTermsOfUseRead.asStateFlow()
    override fun isTermsOfUseRead(): Boolean =
        prefs.getBoolean(KEY_TERMS_OF_USE_READ, false)

    override fun isTermsOfUseRead(ok: Boolean) {
        if (_isTermsOfUseRead.value != ok) {
            prefs.edit { putBoolean(KEY_TERMS_OF_USE_READ, ok) }
            _isTermsOfUseRead.value = ok
        }
    }

    private val _isMainNetworkType = MutableStateFlow(isMainNetworkType())
    override val isMainNetworkType: StateFlow<Boolean> = _isMainNetworkType.asStateFlow()
    override fun isMainNetworkType(): Boolean = prefs.getBoolean(KEY_MAIN_NETWORK_TYPE, true)

    override fun isMainNetworkType(ok: Boolean) {
        if (_isMainNetworkType.value != ok) {
            prefs.edit { putBoolean(KEY_MAIN_NETWORK_TYPE, ok) }
            _isMainNetworkType.value = ok
        }
    }

    private val _selectedAggregator = MutableStateFlow(selectedAggregator())
    override val selectedAggregator: StateFlow<SupportedAggregators> =
        _selectedAggregator.asStateFlow()

    override fun selectedAggregator(): SupportedAggregators {
        val label = prefs.getString(KEY_SELECTED_AGGREGATOR, "") ?: ""
        return SupportedAggregators.fromLabel(label)
    }

    override fun selectedAggregator(label: String) {
        val newValue = SupportedAggregators.fromLabel(label)
        if (_selectedAggregator.value != newValue) {
            prefs.edit { putString(KEY_SELECTED_AGGREGATOR, label) }
            _selectedAggregator.value = newValue
        }
    }

    private val _selectedFilterStateChronicle = MutableStateFlow(selectedFilterStateChronicle())
    override val selectedFilterStateChronicle: StateFlow<FilterStateChronicle> =
        _selectedFilterStateChronicle.asStateFlow()

    override fun selectedFilterStateChronicle(): FilterStateChronicle {
        val pos = prefs.getInt(KEY_FILTER_STATE_CHRONICLE, 1)
        return FilterStateChronicle.fromPos(pos)
    }

    override fun selectedFilterStateChronicle(filterStateChronicle: FilterStateChronicle) {
        if (_selectedFilterStateChronicle.value != filterStateChronicle) {
            prefs.edit { putInt(KEY_FILTER_STATE_CHRONICLE, filterStateChronicle.pos) }
            _selectedFilterStateChronicle.value = filterStateChronicle
        }
    }

    private val _selectedFilterStateBook = MutableStateFlow(selectedFilterStateBook())
    override val selectedFilterStateBook: StateFlow<FilterStateBook> =
        _selectedFilterStateBook.asStateFlow()
    override fun selectedFilterStateBook(): FilterStateBook {
        val pos = prefs.getInt(KEY_FILTER_STATE_BOOK, 0)
        return FilterStateBook.fromPos(pos)
    }
    override fun selectedFilterStateBook(filterStateBook: FilterStateBook) {
        if (_selectedFilterStateBook.value != filterStateBook) {
            prefs.edit { putInt(KEY_FILTER_STATE_BOOK, filterStateBook.pos) }
            _selectedFilterStateBook.value = filterStateBook
        }
    }


}