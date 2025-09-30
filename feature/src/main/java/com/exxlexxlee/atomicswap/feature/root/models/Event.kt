package com.exxlexxlee.atomicswap.feature.root.models

sealed class Event {
    data class SelectedRoute(val route: String) : Event()
}