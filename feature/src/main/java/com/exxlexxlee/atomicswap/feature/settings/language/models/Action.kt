package com.exxlexxlee.atomicswap.feature.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

sealed class Action {
    data class SetLocale(val locale: SupportedLocales) : Action()

}