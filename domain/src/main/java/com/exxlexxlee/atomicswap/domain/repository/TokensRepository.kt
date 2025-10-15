package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.FullCoin
import com.exxlexxlee.atomicswap.core.swap.model.Token

interface TokensRepository {
    fun tokens(): List<Token>

    fun fullCoins(filter: String, limit: Int = 20): List<FullCoin>
    fun topFullCoins(limit: Int = 20): List<FullCoin>
    fun updateTokenAll()
}