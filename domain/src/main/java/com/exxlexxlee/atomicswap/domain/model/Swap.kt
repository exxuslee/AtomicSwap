package com.exxlexxlee.atomicswap.domain.model

import java.math.BigDecimal

data class Swap(
    val swapId: String,
    override val makeId: String,
    override val takeId: String,
    val timestamp: Long,
    override val takerId: String,
    override val makerId: String,
    val swapState: SwapState,
    override val takerToken: Token,
    override val makerToken: Token,
    override val takerRefundAddress: String,
    val takerRefundAddressId: String? = null,
    override val makerRefundAddress: String,
    val makerRefundAddressId: String? = null,
    override val takerRedeemAddress: String,
    val takerRedeemAddressId: String? = null,
    override val makerRedeemAddress: String,
    val makerRedeemAddressId: String? = null,

    val secret: String? = null,
    val secretHash: String,

    val takerRefundTime: Int,
    val makerRefundTime: Int,
    val takerSafeTxTime: Long? = null,
    val makerSafeTxTime: Long? = null,

    val takerSafeTx: String? = null,
    val makerSafeTx: String? = null,
    val takerRedeemTx: String? = null,
    val makerRedeemTx: String? = null,
    val takerRefundTx: String? = null,
    val makerRefundTx: String? = null,

    override val makerExactAmount: BigDecimal,
    override val takerExactAmount: BigDecimal,
    override val makerStartAmount: BigDecimal,
    override val takerStartAmount: BigDecimal,

    val takerSafeAmount: BigDecimal,
    val makerSafeAmount: BigDecimal,
    override val makerFinalAmount: BigDecimal,
    override val takerFinalAmount: BigDecimal,

    ) : Take(
    takeId,
    makeId,
    makerId,
    takerId,
    takerToken,
    makerToken,
    makerRefundAddress,
    makerRedeemAddress,
    takerRefundAddress,
    takerRedeemAddress,
    makerExactAmount,
    takerExactAmount,
    makerStartAmount,
    takerStartAmount,
    makerFinalAmount,
    takerFinalAmount,
)
