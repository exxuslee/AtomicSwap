package com.example.atomicswap.data.repository

import android.content.SharedPreferences
import com.example.atomicswap.domain.repository.SettingsRepository
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

    override fun languageTag(): String {
        return prefs.getString("languageTag", "en") ?: "en"
    }

    override fun languageTag(tag: String) {
        prefs.edit { putString("languageTag", tag) }
    }
}