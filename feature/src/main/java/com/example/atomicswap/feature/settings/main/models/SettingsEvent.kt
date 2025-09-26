package com.example.atomicswap.feature.settings.main.models

sealed class SettingsEvent {
    class IsDark(val newValue: Boolean) : SettingsEvent()
    object OpenTermsScreen : SettingsEvent()
    object OpenLanguageScreen : SettingsEvent()
}