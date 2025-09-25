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

    override fun setDark(value: Boolean) {
        prefs.edit { putBoolean("isDark", value) }
    }
}