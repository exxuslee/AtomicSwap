package com.exxlexxlee.atomicswap.domain.model

enum class SwapState(val value: Int) {
    REQUESTED(1),
    RESPONDED(2),
    INITIATOR_BAILED(3),
    RESPONDER_BAILED(4),
    INITIATOR_REDEEMED(5),
    RESPONDER_REDEEMED(6),
    REFUNDED(7);
}