package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.domain.model.Blockchain
import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.model.SwapState
import com.exxlexxlee.atomicswap.domain.model.Token
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

class SwapRepositoryFakeImpl : SwapRepository {
    
    private val _swaps = MutableStateFlow<List<Swap>>(emptyList())
    override val swaps: StateFlow<List<Swap>> = _swaps.asStateFlow()
    
    private val swapStorage = mutableMapOf<String, Swap>()
    
    init {
        addFakeSwaps()
    }
    
    override fun deleteAllHistory() {
        swapStorage.entries.removeIf { 
            it.value.swapState == SwapState.RESPONDER_REDEEMED ||
            it.value.swapState == SwapState.REFUNDED 
        }
        updateFlow()
    }
    
    override fun swaps(): List<Swap> {
        return swapStorage.values
            .sortedByDescending { it.timestamp }
            .toList()
    }
    
    override fun swap(id: String): Swap {
        return swapStorage[id] ?: throw NoSuchElementException("Swap with id $id not found")
    }
    
    fun insertSwap(swap: Swap) {
        swapStorage[swap.swapId] = swap
        updateFlow()
    }
    
    fun deleteSwap(id: String) {
        swapStorage.remove(id)
        updateFlow()
    }
    
    fun clear() {
        swapStorage.clear()
        updateFlow()
    }
    
    private fun updateFlow() {
        _swaps.update { swaps() }
    }
    
    private fun addFakeSwaps() {
        val fakeSwaps = listOf(
            createFakeSwap(
                swapId = "swap-1",
                makeId = "make-1",
                takeId = "take-1",
                state = SwapState.RESPONDER_REDEEMED,
                timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                takerTokenSymbol = "BTC",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-2",
                makeId = "make-2",
                takeId = "take-2",
                state = SwapState.REQUESTED,
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                takerTokenSymbol = "ETH",
                makerTokenSymbol = "BNB"
            ),
            createFakeSwap(
                swapId = "swap-3",
                makeId = "make-3",
                takeId = "take-3",
                state = SwapState.REFUNDED,
                timestamp = System.currentTimeMillis() - 172800000, // 2 days ago
                takerTokenSymbol = "LTC",
                makerTokenSymbol = "BTC"
            ),
            createFakeSwap(
                swapId = "swap-4",
                makeId = "make-4",
                takeId = "take-4",
                state = SwapState.RESPONDED,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            )
        )
        
        fakeSwaps.forEach { swapStorage[it.swapId] = it }
        updateFlow()
    }
    
    private fun createFakeSwap(
        swapId: String,
        makeId: String,
        takeId: String,
        state: SwapState,
        timestamp: Long,
        takerTokenSymbol: String,
        makerTokenSymbol: String
    ): Swap {
        return Swap(
            swapId = swapId,
            makeId = makeId,
            takeId = takeId,
            timestamp = timestamp,
            takerId = "taker-$swapId",
            makerId = "maker-$swapId",
            swapState = state,
            takerToken = createFakeToken(takerTokenSymbol, Blockchain.Bitcoin),
            makerToken = createFakeToken(makerTokenSymbol, Blockchain.Ethereum),
            takerRefundAddress = "taker-refund-address",
            takerRefundAddressId = "taker-refund-id",
            makerRefundAddress = "maker-refund-address",
            makerRefundAddressId = "maker-refund-id",
            takerRedeemAddress = "taker-redeem-address",
            takerRedeemAddressId = "taker-redeem-id",
            makerRedeemAddress = "maker-redeem-address",
            makerRedeemAddressId = "maker-redeem-id",
            secret = if (state == SwapState.RESPONDER_REDEEMED) "secret-value" else null,
            secretHash = "secret-hash-${swapId}",
            takerRefundTime = 3600,
            makerRefundTime = 7200,
            takerSafeTxTime = if (state != SwapState.REQUESTED) timestamp else null,
            makerSafeTxTime = if (state >= SwapState.RESPONDED) timestamp else null,
            takerSafeTx = if (state != SwapState.REQUESTED) "taker-safe-tx-${swapId}" else null,
            makerSafeTx = if (state >= SwapState.RESPONDED) "maker-safe-tx-${swapId}" else null,
            takerRedeemTx = if (state == SwapState.RESPONDER_REDEEMED) "taker-redeem-tx" else null,
            makerRedeemTx = if (state == SwapState.RESPONDER_REDEEMED) "maker-redeem-tx" else null,
            takerRefundTx = if (state == SwapState.REFUNDED) "taker-refund-tx" else null,
            makerRefundTx = if (state == SwapState.REFUNDED) "maker-refund-tx" else null,
            makerExactAmount = BigDecimal("10.0"),
            takerExactAmount = BigDecimal("1.5"),
            makerStartAmount = BigDecimal("10.5"),
            takerStartAmount = BigDecimal("1.6"),
            takerSafeAmount = BigDecimal("1.5"),
            makerSafeAmount = BigDecimal("10.0"),
            makerFinalAmount = BigDecimal("9.9"),
            takerFinalAmount = BigDecimal("1.49")
        )
    }
    
    private fun createFakeToken(
        symbol: String,
        blockchain: Blockchain
    ): Token {
        return Token(
            id = "token-$symbol",
            symbol = symbol,
            name = when (symbol) {
                "BTC" -> "Bitcoin"
                "ETH" -> "Ethereum"
                "BNB" -> "Binance Coin"
                "LTC" -> "Litecoin"
                else -> symbol
            },
            iconUrl = "https://example.com/icon/$symbol.png",
            contractAddress = if (symbol != "BTC" && symbol != "LTC") "0x123...abc" else null,
            blockchain = blockchain,
            decimal = 18
        )
    }
}