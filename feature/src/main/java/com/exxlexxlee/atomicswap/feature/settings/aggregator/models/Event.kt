package com.exxlexxlee.atomicswap.feature.settings.aggregator.models

sealed class Event {
    data class Select(val label: String) : Event()
}