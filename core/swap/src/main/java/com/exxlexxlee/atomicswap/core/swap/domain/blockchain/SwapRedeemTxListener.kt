package com.exxlexxlee.atomicswap.core.swap.domain.blockchain


interface SwapRedeemTxListener {
    suspend fun onRedeemTxSeen(redeemTx: String, secret: String)
}