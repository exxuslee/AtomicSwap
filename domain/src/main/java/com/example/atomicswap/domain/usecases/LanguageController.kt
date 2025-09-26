package com.example.atomicswap.domain.usecases

import com.example.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class LanguageController(
    private val settingsRepository: SettingsRepository,
) {
    private val _languageTag = MutableStateFlow(settingsRepository.languageTag())
    val languageTag: StateFlow<String> = _languageTag.asStateFlow()

    fun setLanguageTag(tag: String) {
        settingsRepository.languageTag(tag)
        _languageTag.value = tag
    }

    var currentLocale: Locale = Locale.forLanguageTag(_languageTag.value)

    var currentLocaleTag: String
        get() = currentLocale.toLanguageTag()
        set(value) {
            currentLocale = Locale.forLanguageTag(value)
        }

    val currentLanguageName: String
        get() = getName(currentLocaleTag)

    val currentLanguage: String
        get() = currentLocale.language

    fun getName(tag: String): String {
        return Locale.forLanguageTag(tag)
            .getDisplayName(currentLocale)
            .replaceFirstChar(Char::uppercase)
    }

    fun getNativeName(tag: String): String {
        val locale = Locale.forLanguageTag(tag)
        return locale.getDisplayName(locale).replaceFirstChar(Char::uppercase)
    }

}



