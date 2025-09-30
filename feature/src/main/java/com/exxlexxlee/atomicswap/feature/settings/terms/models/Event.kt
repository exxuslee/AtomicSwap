package com.exxlexxlee.atomicswap.feature.settings.terms.models

sealed class Event {
    data object ReadTerms: Event()
    data object PopBackStack: Event()
}