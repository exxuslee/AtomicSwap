package com.exxlexxlee.atomicswap.feature.chronicle.main.models

sealed class Action {
    data class OpenSwap(val swapId: String) : Action()

}