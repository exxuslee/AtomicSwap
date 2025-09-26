package com.example.atomicswap.feature.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

sealed class Action {
    data class SetLocale(val locale: SupportedLocales) : Action()
    data object PopBackStack: Action()

}