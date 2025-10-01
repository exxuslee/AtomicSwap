package com.exxlexxlee.atomicswap.core.swap.model

open class RedeemTx(
    val txHash: ByteArray,
    val secret: ByteArray = byteArrayOf(),
)