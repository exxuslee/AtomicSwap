package com.exxlexxlee.atomicswap.core.swap.model

class RegisterBlockchain<A, B>(
    val coinCode: String,
    val adapter: A,
    val tokenAddress: B,
    val nativeCoinUid: String,
    val tokenCoinUid: String,
)