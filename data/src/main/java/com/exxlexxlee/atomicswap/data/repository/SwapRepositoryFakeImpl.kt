package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.domain.model.*
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import kotlin.random.Random

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
                swapId = "swap-4",
                makeId = "make-4",
                takeId = "take-4",
                state = SwapState.RESPONDED,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-5",
                makeId = "make-5",
                takeId = "take-5",
                state = SwapState.RESPONDED,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-6",
                makeId = "make-6",
                takeId = "take-6",
                state = SwapState.RESPONDED,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-7",
                makeId = "make-7",
                takeId = "take-7",
                state = SwapState.RESPONDED,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-8",
                makeId = "make-8",
                takeId = "take-8",
                state = SwapState.REQUESTED,
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                takerTokenSymbol = "ETH",
                makerTokenSymbol = "BNB"
            ),
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
        val makerToken = createFakeToken(makerTokenSymbol, Blockchain.Ethereum(isMain = true))
        val takerToken = createFakeToken(takerTokenSymbol, Blockchain.Bitcoin(isMain = true))

        val make = Make(
            makeId = makeId,
            makerId = "maker-${makeId}",
            makerToken = makerToken,
            takerToken = takerToken,
            makerRefundAddress = "maker-refund-address-${makeId}",
            makerRedeemAddress = "maker-redeem-address-${makeId}",
            makerExactAmount = BigDecimal("10.0"),
            takerExactAmount = BigDecimal("1.5"),
            makerStartAmount = BigDecimal("10.5"),
            takerStartAmount = BigDecimal("1.6")
        )

        val take = Take(
            make = make,
            takeId = takeId,
            takerId = "taker-${takeId}",
            takerRefundAddress = "taker-refund-address-${takeId}",
            takerRedeemAddress = "taker-redeem-address-${takeId}",
            makerFinalAmount = BigDecimal("10.0"),
            takerFinalAmount = BigDecimal("1.5")
        )

        return Swap(
            swapId = swapId,
            take = listOf(take),
            takeId = takeId,
            make = make,
            timestamp = timestamp,
            swapState = state,
            takerRefundAddressId = "taker-refund-id",
            makerRefundAddressId = "maker-refund-id",
            takerRedeemAddressId = "taker-redeem-id",
            makerRedeemAddressId = "maker-redeem-id",
            isRead = Random.nextBoolean(),
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
            takerSafeAmount = BigDecimal("1.5"),
            makerSafeAmount = BigDecimal("10.0")
        )
    }

    private fun createFakeToken(
        symbol: String,
        blockchain: Blockchain
    ): Token {
        val coin = Coin(
            id = "token-$symbol",
            symbol = symbol,
            name = when (symbol) {
                "BTC" -> "Bitcoin"
                "ETH" -> "Ethereum"
                "BNB" -> "Binance Coin"
                "LTC" -> "Litecoin"
                else -> symbol
            },
            iconUrl = when (symbol) {
                "BTC" -> "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"
                "ETH" -> "https://assets.coingecko.com/coins/images/279/small/ethereum.png"
                "BNB" -> "https://assets.coingecko.com/coins/images/825/small/bnb-icon2_2x.png"
                "LTC" -> "https://assets.coingecko.com/coins/images/2/small/litecoin.png"
                else -> ""
            }
        )
        return Token(
            coin = coin,
            contractAddress = if (symbol != "BTC" && symbol != "LTC") "0x123...abc" else null,
            blockchain = blockchain,
            decimal = 18
        )
    }
}