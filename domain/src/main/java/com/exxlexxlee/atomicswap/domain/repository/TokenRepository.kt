package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.domain.model.Token

interface TokenRepository {
    fun token(id: String): Token
    fun tokenAll(): List<Token>
    fun updateTokenAll()
}