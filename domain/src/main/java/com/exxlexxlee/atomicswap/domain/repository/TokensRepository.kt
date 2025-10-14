package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.Token

interface TokensRepository {
    fun tokens(): List<Token>
    fun updateTokenAll()
}