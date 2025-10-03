package com.exxlexxlee.atomicswap.feature.tabs.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

data class ViewState(
    val languageItems: List<SupportedLocales> = SupportedLocales.entries,
)