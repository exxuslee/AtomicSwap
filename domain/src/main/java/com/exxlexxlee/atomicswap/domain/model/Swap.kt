package com.exxlexxlee.atomicswap.domain.model

import java.math.BigDecimal

class Swap(
    val id: String,
    val timestamp: Long,
    val takerId: String,
    val makerId: String,
    val swapState: SwapState,
    val takerToken: Token,
    val makerToken: Token,
    val takerRefundAddress: String,
    val takerRefundAddressId: String,
    val makerRefundAddress: String,
    val makerRefundAddressId: String,
    val takerRedeemAddress: String,
    val takerRedeemAddressId: String,
    val makerRedeemAddress: String,
    val makerRedeemAddressId: String,
    val secret: String,
    val secretHash: String,
    val takerRefundTime: Int,
    val makerRefundTime: Int,
    val takerSafeTxTime: Long,
    val makerSafeTxTime: Long,
    val takerSafeTx: String,
    val makerSafeTx: String,
    val takerRedeemTx: String,
    val makerRedeemTx: String,
    val takerRefundTx: String,
    val makerRefundTx: String,

    val takerSafeAmount: BigDecimal,
    val makerSafeAmount: BigDecimal,

    val makerExactAmount: BigDecimal,
    val takerExactAmount: BigDecimal,

    val makerStartAmount: BigDecimal,
    val takerStartAmount: BigDecimal,

    val makerFinalAmount: BigDecimal,
    val takerFinalAmount: BigDecimal,

    )
