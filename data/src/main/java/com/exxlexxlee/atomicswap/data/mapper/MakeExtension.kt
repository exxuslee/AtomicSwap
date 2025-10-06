package com.exxlexxlee.atomicswap.data.mapper

import com.exxlexxlee.atomicswap.core.database.MakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.AmountType
import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.core.swap.model.toStorageName
import java.math.BigDecimal

internal fun MakeEntity.toDomain(): Make {
    val makeTokenCoin = Coin(
        id = makerTokenCoinId,
        symbol = makerTokenCoinSymbol,
        name = makerTokenCoinName,
        iconUrl = makerTokenCoinIconUrl
    )

    val takerTokenCoin = Coin(
        id = takerTokenCoinId,
        symbol = takerTokenCoinSymbol,
        name = takerTokenCoinName,
        iconUrl = takerTokenCoinIconUrl
    )

    val makerToken = Token(
        id = makerTokenCoinSymbol.lowercase(),
        coin = makeTokenCoin,
        contractAddress = makerTokenContractAddress,
        blockchain = Blockchain.valueOf(makerTokenBlockchain),
        decimal = makerTokenDecimal.toInt()
    )

    val takerToken = Token(
        id = takerTokenCoinSymbol.lowercase(),
        coin = takerTokenCoin,
        contractAddress = takerTokenContractAddress,
        blockchain = Blockchain.valueOf(takerTokenBlockchain),
        decimal = takerTokenDecimal.toInt()
    )

    return Make(
        makeId = makeId,
        makerId = makerId,
        makerToken = makerToken,
        takerToken = takerToken,
        refundAddress = makerRefundAddress,
        redeemAddress = makerRedeemAddress,
        amount = AmountType.ExactIn(
            makerExactAmount = BigDecimal(makerExactAmount),
            takerStartAmount = BigDecimal(takerStartAmount)
        ),
        priceType = PriceType.Fixed,
        isOn = true,
        reservedAmount = BigDecimal.ZERO,
        refundTime = 0L,
        timestamp = 0L,
    )
}

internal fun Make.toMakeEntity(): MakeEntity {
    val makerExactAmount: BigDecimal
    val takerExactAmount: BigDecimal
    val makerStartAmount: BigDecimal
    val takerStartAmount: BigDecimal
    when (val amt = amount) {
        is AmountType.ExactIn -> {
            makerExactAmount = amt.makerExactAmount
            takerStartAmount = amt.takerStartAmount
            makerStartAmount = amt.makerExactAmount
            takerExactAmount = amt.takerStartAmount
        }
        is AmountType.ExactOut -> {
            makerStartAmount = amt.makerStartAmount
            takerExactAmount = amt.takerExactAmount
            makerExactAmount = amt.makerStartAmount
            takerStartAmount = amt.takerExactAmount
        }
    }

    return MakeEntity(
        makeId = makeId,
        makerId = makerId,
        makerRefundAddress = refundAddress,
        makerRedeemAddress = redeemAddress,
        makerExactAmount = makerExactAmount.toPlainString(),
        takerExactAmount = takerExactAmount.toPlainString(),
        makerStartAmount = makerStartAmount.toPlainString(),
        takerStartAmount = takerStartAmount.toPlainString(),
        makerTokenCoinId = makerToken.coin.id,
        makerTokenCoinSymbol = makerToken.coin.symbol,
        makerTokenCoinName = makerToken.coin.name,
        makerTokenCoinIconUrl = makerToken.coin.iconUrl,
        makerTokenContractAddress = makerToken.contractAddress,
        makerTokenBlockchain = makerToken.blockchain.toStorageName(),
        makerTokenDecimal = makerToken.decimal,
        takerTokenCoinId = takerToken.coin.id,
        takerTokenCoinSymbol = takerToken.coin.symbol,
        takerTokenCoinName = takerToken.coin.name,
        takerTokenCoinIconUrl = takerToken.coin.iconUrl,
        takerTokenContractAddress = takerToken.contractAddress,
        takerTokenBlockchain = takerToken.blockchain.toStorageName(),
        takerTokenDecimal = takerToken.decimal
    )
}