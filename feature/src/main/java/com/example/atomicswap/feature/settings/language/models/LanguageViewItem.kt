package com.example.atomicswap.feature.settings.language.models

data class LanguageViewItem(
    val localeType: LocaleType,
    val name: String,
    val nativeName: String,
    val icon: Int,
    val current: Boolean,
)