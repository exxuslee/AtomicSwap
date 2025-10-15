package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.swap.model.FullCoin
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.data.mapper.toDomain
import com.exxlexxlee.atomicswap.domain.repository.TokensRepository
import com.google.gson.Gson
import io.horizontalsystems.marketkit.MarketKit
import timber.log.Timber

class TokensRepositoryImpl(
    private val marketKit: MarketKit
) : TokensRepository {

    override fun tokens(): List<Token> {
        val tokens = marketKit.tokens("").map { it.toDomain() }
        Timber.d(Gson().toJson(tokens))
        return tokens
    }

    override fun fullCoins(
        filter: String,
        limit: Int
    ): List<FullCoin> {
        return marketKit.fullCoins(filter, limit).mapNotNull { fullCoin ->
            val domainCoin = fullCoin.coin.toDomain()
            val domainTokens = fullCoin.tokens
                .map { it.toDomain() }.filter { it.blockchain.isSupported() }
            if (domainTokens.isNotEmpty()) FullCoin(domainCoin, domainTokens) else null
        }
    }

    override fun topFullCoins(limit: Int): List<FullCoin> {
        return marketKit.topFullCoins(limit).mapNotNull { fullCoin ->
            val domainCoin = fullCoin.coin.toDomain()
            val domainTokens = fullCoin.tokens
                .map { it.toDomain() }.filter { it.blockchain.isSupported() }
            if (domainTokens.isNotEmpty()) FullCoin(domainCoin, domainTokens) else null
        }
    }

    override fun updateTokenAll() {

    }
}