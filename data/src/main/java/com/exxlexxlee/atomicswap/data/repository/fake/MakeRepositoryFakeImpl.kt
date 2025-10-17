package com.exxlexxlee.atomicswap.data.repository.fake

import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.domain.repository.MakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal

class MakeRepositoryFakeImpl(
    initialMakes: List<Make> = DEFAULT_MAKES,
) : MakeRepository {

    private val makesState = MutableStateFlow(initialMakes)

    override val makes: Flow<List<Make>> = makesState.asStateFlow()

    override fun make(makeId: String): Make =
        makesState.value.firstOrNull { it.makeId == makeId }
            ?: error("Make with id=$makeId not found")

    override fun myMake(makerId: String): List<Make> =
        makesState.value.filter { it.makerId == makerId }

    fun setMakes(newMakes: List<Make>) {
        makesState.value = newMakes
    }

    fun addMake(make: Make) {
        makesState.value = makesState.value + make
    }

    fun updateMake(updated: Make) {
        makesState.value = makesState.value.map { current ->
            if (current.makeId == updated.makeId) updated else current
        }
    }

    fun removeMake(makeId: String) {
        makesState.value = makesState.value.filterNot { it.makeId == makeId }
    }

    companion object {
        private val btc = Coin(
            symbol = "BTC",
            name = "Bitcoin",
            iconUrl = "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"
        )
        private val eth = Coin(
            symbol = "ETH",
            name = "Ethereum",
            iconUrl = "https://assets.coingecko.com/coins/images/279/small/ethereum.png"
        )
        private val usdt = Coin(
            symbol = "USDT",
            name = "Tether",
            iconUrl = "https://assets.coingecko.com/coins/images/2/small/litecoin.png"
        )

        private val btcToken = Token(
            coin = btc,
            contractAddress = null,
            blockchain = Blockchain.Bitcoin,
            decimal = 8,
        )
        private val ethToken = Token(
            coin = eth,
            contractAddress = null,
            blockchain = Blockchain.Ethereum,
            decimal = 18,
        )
        private val usdtTronToken = Token(
            coin = usdt,
            contractAddress = "TXYZ...USDT",
            blockchain = Blockchain.Tron,
            decimal = 6,
        )

        val DEFAULT_MAKES: List<Make> = listOf(
            Make(
                makeId = "mk_001",
                makerId = "user_1",
                makerToken = btcToken,
                takerToken = ethToken,
                refundAddress = "bc1qrefundaddrxxxxxxxxxx",
                redeemAddress = "0xRedeemAddr111111111111111111111111111111",
                adAmount = BigDecimal("0.10000000"),
                discount = 1,
                isOn = true,
                reservedAmount = BigDecimal("0.00000000"),
                refundTime = 3_600_000L,
                timestamp = 1_695_000_000_000L,
            ),
            Make(
                makeId = "mk_002",
                makerId = "user_2",
                makerToken = ethToken,
                takerToken = usdtTronToken,
                refundAddress = "0xRefundAddr222222222222222222222222222222",
                redeemAddress = "TReDeemAddr2222222222222",
                adAmount = BigDecimal("0.10000000"),
                discount = 1,
                isOn = true,
                reservedAmount = BigDecimal("0.500000000000000000"),
                refundTime = 7_200_000L,
                timestamp = 1_695_000_100_000L,
            ),
            Make(
                makeId = "mk_003",
                makerId = "user_1",
                makerToken = usdtTronToken,
                takerToken = btcToken,
                refundAddress = "TRefundAddr3333333333333",
                redeemAddress = "bc1qredeemaddrxxxxxxxxxx",
                adAmount = BigDecimal("0.10000000"),
                discount = 1,
                isOn = false,
                reservedAmount = BigDecimal("100.000000"),
                refundTime = 10_800_000L,
                timestamp = 1_695_000_200_000L,
            ),
        )
    }
}


