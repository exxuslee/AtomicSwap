package com.example.atomicswap.feature.settings.main.models

sealed class SettingsAction {
    data object OpenTermsScreen : SettingsAction()
    data object OpenLanguageScreen : SettingsAction()

}