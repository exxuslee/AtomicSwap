package com.exxlexxlee.atomicswap.data.repository

import android.content.SharedPreferences
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import androidx.core.content.edit

class SettingsRepositoryImpl(
    private val prefs: SharedPreferences
) : SettingsRepository {

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

    override fun isTermsOfUseRead(): Boolean {
        return prefs.getBoolean("isTermsOfUseRead", false)
    }

    override fun isTermsOfUseRead(ok: Boolean) {
        prefs.edit { putBoolean("isTermsOfUseRead", ok) }
    }

    override fun selectedAggregator(): String {
        return prefs.getString("selectedAggregator", "") ?: ""
    }

    override fun selectedAggregator(label: String) {
        prefs.edit { putString("selectedAggregator", label) }
    }
}