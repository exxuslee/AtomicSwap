package com.exxlexxlee.atomicswap.core.swap.domain.blockchain

interface SwapBlockchainCreator {
    fun create(): SwapBlockchain
}