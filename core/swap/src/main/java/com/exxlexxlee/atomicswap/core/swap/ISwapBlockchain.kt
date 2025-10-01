package com.exxlexxlee.atomicswap.core.swap

import com.exxlexxlee.atomicswap.core.swap.model.BailTx
import com.exxlexxlee.atomicswap.core.swap.model.PublicKey
import com.exxlexxlee.atomicswap.core.swap.model.RedeemTx
import java.math.BigInteger


interface ISwapBlockchain {
    val chainId: BigInteger
    val addressContract: String

    suspend fun sendBailTx(
        redeemPKH: ByteArray,
        secretHash: ByteArray,
        refundPKH: ByteArray,
        refundTime: Long,
        amount: String,
    ): BailTx

    suspend fun setBailTxListener(
        listener: ISwapBailTxListener,
        redeemPKH: ByteArray,
        secretHash: ByteArray,
        refundPKH: ByteArray,
        refundTime: Long,
        amount: String,
    )

    suspend fun sendRedeemTx(
        redeemPKH: ByteArray,
        redeemPKId: String,
        secret: ByteArray,
        secretHash: ByteArray,
        refundPKH: ByteArray,
        refundTime: Long,
        bailTx: BailTx,
    ): RedeemTx

    suspend fun sendRevealTx(
        swapId: ByteArray,
        chainId: BigInteger,
        chainContract: String,
        secret: ByteArray,
        bailTx: BailTx,
    ): BailTx

    suspend fun sendRefundTx(
        redeemPKH: ByteArray,
        secretHash: ByteArray,
        refundPKH: ByteArray,
        refundPKId: String,
        refundTime: Long,
        bailTx: BailTx,
    ): BailTx

    suspend fun setRedeemTxListener(listener: ISwapRedeemTxListener, bailTx: BailTx)
    fun getRedeemPublicKey(): PublicKey
    fun getRefundPublicKey(): PublicKey
    fun serializeBailTx(bailTx: BailTx): ByteArray
    fun deserializeBailTx(data: ByteArray): BailTx?
    fun serializeRedeemTx(redeemTx: RedeemTx): ByteArray
    fun deserializeRedeemTx(data: ByteArray): RedeemTx
    fun tomorrow(time: Long): Long
    fun dayAfterTomorrow(time: Long): Long
}

interface ISwapBailTxListener {
    suspend fun onBailTransactionSeen(bailTx: BailTx)
}

interface ISwapRedeemTxListener {
    suspend fun onRedeemTxSeen(redeemTx: RedeemTx)
}





