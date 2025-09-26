package com.example.atomicswap.feature.settings.language.models

sealed class Event {
    data class Select(val type: LocaleType) : Event()
}