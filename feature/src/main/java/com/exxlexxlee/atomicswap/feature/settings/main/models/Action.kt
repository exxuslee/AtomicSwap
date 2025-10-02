package com.exxlexxlee.atomicswap.feature.settings.main.models

sealed class Action {
    data object LocaleStorageDialog : Action()
    data object ConnectWcDialog : Action()

}