package com.example.atomicswap.feature.settings.settings.models

sealed class Event {
    class IsDark(val newValue: Boolean) : Event()
    object OpenTermsScreen : Event()
    object OpenLanguageScreen : Event()
    object OpenNotificationScreen : Event()
    object OpenDonateScreen : Event()
    object OpenAboutScreen : Event()
}