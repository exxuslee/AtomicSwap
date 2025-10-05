package com.exxlexxlee.atomicswap.core.swap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.Swap

interface SwapRepository {
    fun updateSafe(swap: Swap)
}