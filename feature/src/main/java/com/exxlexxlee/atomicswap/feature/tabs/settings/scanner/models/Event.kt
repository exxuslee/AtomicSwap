package com.exxlexxlee.atomicswap.feature.tabs.settings.scanner.models

sealed class Event {
    data class PermissionState(val isGranted: Boolean) : Event()
    data object RequestPermission : Event()
}