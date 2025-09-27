package com.example.atomicswap.feature.settings.main.models

sealed class Action {
    data object OpenTermsScreen : Action()
    data object OpenLanguageScreen : Action()
    data object OpenNotificationScreen : Action()

}