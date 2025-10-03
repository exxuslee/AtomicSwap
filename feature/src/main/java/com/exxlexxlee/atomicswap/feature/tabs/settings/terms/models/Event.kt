package com.exxlexxlee.atomicswap.feature.tabs.settings.terms.models

sealed class Event {
    data object ReadTerms: Event()
}