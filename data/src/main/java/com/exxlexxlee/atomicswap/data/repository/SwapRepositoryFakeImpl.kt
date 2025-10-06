package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.swap.model.AmountType
import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.core.swap.model.SwapState
import com.exxlexxlee.atomicswap.core.swap.model.Take
import com.exxlexxlee.atomicswap.core.swap.model.Token
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

    override suspend fun deleteAllHistory() {
        swapStorage.entries.removeIf {
            it.value.swapState == SwapState.ResponderRedeemed ||
                    it.value.swapState == SwapState.Refunded
        }
        updateFlow()
    }

    override fun swapsAll(): List<Swap> {
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
        _swaps.update { swapsAll() }
    }

    private fun addFakeSwaps() {
        val fakeSwaps = listOf(
            createFakeSwap(
                swapId = "swap-1",
                makeId = "make-1",
                takeId = "take-1",
                state = SwapState.ResponderRedeemed,
                timestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                takerTokenSymbol = "BTC",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-2",
                makeId = "make-2",
                takeId = "take-2",
                state = SwapState.Requested,
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                takerTokenSymbol = "ETH",
                makerTokenSymbol = "BNB"
            ),
            createFakeSwap(
                swapId = "swap-4",
                makeId = "make-4",
                takeId = "take-4",
                state = SwapState.Responded,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-5",
                makeId = "make-5",
                takeId = "take-5",
                state = SwapState.Responded,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-6",
                makeId = "make-6",
                takeId = "take-6",
                state = SwapState.Responded,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-7",
                makeId = "make-7",
                takeId = "take-7",
                state = SwapState.Responded,
                timestamp = System.currentTimeMillis() - 7200000, // 2 hours ago
                takerTokenSymbol = "BNB",
                makerTokenSymbol = "ETH"
            ),
            createFakeSwap(
                swapId = "swap-8",
                makeId = "make-8",
                takeId = "take-8",
                state = SwapState.Requested,
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
            refundAddress = "maker-refund-address-${makeId}",
            redeemAddress = "maker-redeem-address-${makeId}",
            amount = AmountType.ExactIn(
                makerExactAmount = BigDecimal("10.0"),
                takerStartAmount = BigDecimal("1.6")
            ),
            priceType = PriceType.Fixed,
            isOn = true,
            reservedAmount = BigDecimal.ZERO,
            refundTime = 0L,
            timestamp = timestamp - 1000,
        )

        val take = Take(
            takeId = takeId,
            make = make,
            takerId = "taker-${takeId}",
            redeemAddress = "taker-redeem-address-${takeId}",
            refundAddress = "taker-refund-address-${takeId}",
            isConfirmed = state.step >= SwapState.Confirmed.step,
            makerFinalAmount = BigDecimal("10.0"),
            takerFinalAmount = BigDecimal("1.5"),
            takerSafeAmount = BigDecimal("1.5"),
            makerSafeAmount = BigDecimal("10.0")
        )

        return Swap(
            swapId = swapId,
            take = take,
            timestamp = timestamp,
            swapState = state,
            isRead = Random.nextBoolean(),
            secret = if (state == SwapState.ResponderRedeemed) "secret-value" else null,
            secretHash = "secret-hash-${swapId}",
            takerSafeTxTime = if (state != SwapState.Requested) timestamp else null,
            makerSafeTxTime = if (state.step >= SwapState.Responded.step) timestamp else null,
            takerSafeTx = if (state != SwapState.Requested) "taker-safe-tx-${swapId}" else null,
            makerSafeTx = if (state.step >= SwapState.Responded.step) "maker-safe-tx-${swapId}" else null,
            takerRedeemTx = if (state == SwapState.ResponderRedeemed) "taker-redeem-tx" else null,
            makerRedeemTx = if (state == SwapState.ResponderRedeemed) "maker-redeem-tx" else null,
            takerRefundTx = if (state == SwapState.Refunded) "taker-refund-tx" else null,
            makerRefundTx = if (state == SwapState.Refunded) "maker-refund-tx" else null,
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
            id = symbol,
            coin = coin,
            contractAddress = if (symbol != "BTC" && symbol != "LTC") "0x123...abc" else null,
            blockchain = blockchain,
            decimal = 18
        )
    }
}