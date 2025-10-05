package com.exxlexxlee.atomicswap.core.swap.domain.blockchain


interface SwapBailTxListener {
    suspend fun onBailTransactionSeen(bailTx: String)

}

