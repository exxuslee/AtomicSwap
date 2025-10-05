package com.exxlexxlee.atomicswap.core.swap.domain.blockchain

import com.exxlexxlee.atomicswap.core.swap.model.PublicKey
import java.math.BigDecimal
import java.math.BigInteger


interface SwapBlockchain {
    val chainId: BigInteger
    val addressContract: String

    suspend fun sendBailTx(
        redeemPKH: String,
        secretHash: String,
        refundPKH: String,
        refundTime: Long,
        amount: BigDecimal,
    ): String

    suspend fun setBailTxListener(
        listener: SwapBailTxListener,
        redeemPKH: String,
        secretHash: String,
        refundPKH: String,
        refundTime: Long,
        amount: BigDecimal,
    )

    suspend fun sendRedeemTx(
        redeemPKH: String,
        secret: String,
        secretHash: String,
        refundPKH: String,
        refundTime: Long,
        bailTx: String,
    ): String



    suspend fun sendRefundTx(
        redeemPKH: String,
        secretHash: String,
        refundPKH: String,
        refundTime: Long,
        bailTx: String,
    ): String

    suspend fun setRedeemTxListener(listener: SwapRedeemTxListener, bailTx: String)
    fun getRedeemPublicKey(): PublicKey
    fun getRefundPublicKey(): PublicKey
    fun tomorrow(time: Long): Long
    fun dayAfterTomorrow(time: Long): Long
}







