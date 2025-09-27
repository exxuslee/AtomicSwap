package com.example.atomicswap.feature.settings.notification.models

sealed class Event {
    data object PopBackStack: Event()
    data object OnBottomReached : Event()
    data class OnClick(val id: Long) : Event()
    data class RevealCardIndex(val id: Long) : Event()
    data class Delete(val id: Long) : Event()
    data class MarkAsRead(val id: Long) : Event()

}