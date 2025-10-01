package com.exxlexxlee.atomicswap.core.swap.model

import java.math.BigDecimal

class AtomicSwapFee(
    val feeInSafeInitiator: BigDecimal,
    val feeInSafeResponder: BigDecimal,
    val coefficientFeeInitiator: BigDecimal,
    val coefficientFeeResponder: BigDecimal,
)