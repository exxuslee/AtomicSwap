package com.exxlexxlee.atomicswap.feature.settings.main.models

sealed class Event {
    class IsDark(val newValue: Boolean) : Event()
    class ToggleWalletConnect(val enabled: Boolean) : Event()
    object OpenTermsScreen : Event()
    object OpenLanguageScreen : Event()
    object OpenNotificationScreen : Event()
    object OpenDonateScreen : Event()
    object OpenAboutScreen : Event()
    object OpenClearStorage : Event()
    object ConfirmClearStorage : Event()
}