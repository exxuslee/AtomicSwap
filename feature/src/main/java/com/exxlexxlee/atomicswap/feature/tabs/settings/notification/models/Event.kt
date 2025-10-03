package com.exxlexxlee.atomicswap.feature.tabs.settings.notification.models

sealed class Event {
    data object BottomReached : Event()
    data class Click(val id: Long) : Event()
    data class Reveal(val id: Long) : Event()
    data class Delete(val id: Long) : Event()
    data class MarkAsRead(val id: Long) : Event()

}