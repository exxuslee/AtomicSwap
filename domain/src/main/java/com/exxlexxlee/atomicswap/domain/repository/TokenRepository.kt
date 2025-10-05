package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.core.swap.model.Token

interface TokenRepository {
    fun token(id: String): Token
    fun tokenAll(): List<Token>
    fun updateTokenAll()
}