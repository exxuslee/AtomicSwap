package com.example.atomicswap.feature.settings.notification.models

sealed class Event {
    data object PopBackStack: Event()
    data object BottomReached : Event()
    data class Click(val id: Long) : Event()
    data class Reveal(val id: Long) : Event()
    data class Delete(val id: Long) : Event()
    data class MarkAsRead(val id: Long) : Event()

}