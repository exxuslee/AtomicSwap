package com.example.atomicswap.feature.settings.models

sealed class SettingsEvent {
    class IsDark(val newValue: Boolean) : SettingsEvent()
    object MainAction : SettingsEvent()
}