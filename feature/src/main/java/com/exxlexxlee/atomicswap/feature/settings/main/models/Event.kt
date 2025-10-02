package com.exxlexxlee.atomicswap.feature.settings.main.models

sealed class Event {
    class IsDark(val newValue: Boolean) : Event()
    object OpenWalletConnectDialog : Event()

    object OpenClearStorageDialog : Event()
    object ConfirmClearStorage : Event()
}