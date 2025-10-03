package com.exxlexxlee.atomicswap.domain.model

sealed class SwapState(val step: Int) {

    data object Requested : SwapState(1)
    data object Responded : SwapState(2)
    data object InitiatorBailed : SwapState(3)
    data object ResponderBailed : SwapState(4)
    data object InitiatorRedeemed : SwapState(5)
    data object ResponderRedeemed : SwapState(6)
    data object Refunded : SwapState(7)

    companion object {
        fun fromValue(value: Int): SwapState =
            when (value) {
                1 -> Requested
                2 -> Responded
                3 -> InitiatorBailed
                4 -> ResponderBailed
                5 -> InitiatorRedeemed
                6 -> ResponderRedeemed
                7 -> Refunded
                else -> throw IllegalArgumentException("Unknown SwapState value=$value")
            }
    }


}