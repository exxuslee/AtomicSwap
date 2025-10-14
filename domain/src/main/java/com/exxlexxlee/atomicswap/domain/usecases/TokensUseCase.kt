package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.core.swap.model.FullCoin
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.domain.repository.TokensRepository

interface TokensUseCase {
    fun tokens(): List<Token>
    fun fullCoins(filter: String, limit: Int = 20): List<FullCoin>

    class Base(
        private val tokensRepository: TokensRepository
    ) : TokensUseCase {
        override fun tokens(): List<Token> {
            return tokensRepository.tokens()
        }

        override fun fullCoins(filter: String, limit: Int): List<FullCoin> {
            return tokensRepository.fullCoins(filter, limit)
        }
    }
}