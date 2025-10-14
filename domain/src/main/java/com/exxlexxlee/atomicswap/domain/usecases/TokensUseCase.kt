package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.domain.repository.TokensRepository

interface TokensUseCase {
    fun tokens(): List<Token>

    class Base(
        private val tokensRepository: TokensRepository
    ) : TokensUseCase {
        override fun tokens(): List<Token> {
            return tokensRepository.tokens()
        }
    }
}