package com.exxlexxlee.atomicswap.core.swap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.Swap

interface SwapRepository {
    suspend fun updateSwap(swap: Swap)
}