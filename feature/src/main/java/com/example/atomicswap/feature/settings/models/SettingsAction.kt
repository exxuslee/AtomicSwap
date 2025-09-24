package com.example.atomicswap.feature.settings.models

sealed class SettingsAction {
    data object OpenMainScreen : SettingsAction()

}