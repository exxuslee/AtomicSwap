package com.example.atomicswap.feature.settings.language.models

import com.hwasfy.localize.util.SupportedLocales

data class LanguageViewItem(
    val localeType: SupportedLocales,
    val name: String,
    val nativeName: String,
    val icon: Int,
    val current: Boolean,
)