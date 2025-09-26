package com.example.atomicswap.feature.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

data class ViewState(
    val languageItems: List<SupportedLocales> = SupportedLocales.entries,
)