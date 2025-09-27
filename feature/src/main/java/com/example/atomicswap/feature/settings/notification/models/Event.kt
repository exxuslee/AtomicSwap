package com.example.atomicswap.feature.settings.notification.models

import com.hwasfy.localize.util.SupportedLocales

sealed class Event {
    data object PopBackStack: Event()
    data class RevealCardIndex(val id: Long) : Event()
    data class Delete(val id: Long) : Event()
    data class MarkAsRead(val id: Long) : Event()
}