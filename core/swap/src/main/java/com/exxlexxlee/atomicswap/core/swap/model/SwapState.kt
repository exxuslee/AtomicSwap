package com.exxlexxlee.atomicswap.core.swap.model

sealed class SwapState(val step: Int) {
    data object Requested : SwapState(1)
    data object Responded : SwapState(2)
    data object Confirmed : SwapState(3)
    data object InitiatorBailed : SwapState(4)
    data object ResponderBailed : SwapState(5)
    data object InitiatorRedeemed : SwapState(6)
    data object ResponderRedeemed : SwapState(7)
    data object Refunded : SwapState(0)

    companion object {
        fun fromValue(value: Int): SwapState =
            when (value) {
                1 -> Requested
                2 -> Responded
                3 -> Confirmed
                4 -> InitiatorBailed
                5 -> ResponderBailed
                6 -> InitiatorRedeemed
                7 -> ResponderRedeemed
                0 -> Refunded
                else -> throw IllegalArgumentException("Unknown SwapState value=$value")
            }
    }
}