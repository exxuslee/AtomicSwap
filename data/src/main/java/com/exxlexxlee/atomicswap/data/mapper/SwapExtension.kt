package com.exxlexxlee.atomicswap.data.mapper

import com.exxlexxlee.atomicswap.core.database.MakeEntity
import com.exxlexxlee.atomicswap.core.database.SwapEntity
import com.exxlexxlee.atomicswap.core.database.TakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.AmountType
import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.core.swap.model.SwapState
import com.exxlexxlee.atomicswap.core.swap.model.Take
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

internal fun TakeEntity.toDomain(make: Make): Take {
    return Take(
        takeId = takeId,
        make = make,
        takerId = takerId,
        redeemAddress = takerRedeemAddress,
        refundAddress = takerRefundAddress,
        isConfirmed = false,
        makerFinalAmount = BigDecimal(makerFinalAmount),
        takerFinalAmount = BigDecimal(takerFinalAmount),
        takerSafeAmount = BigDecimal.ZERO,
        makerSafeAmount = BigDecimal.ZERO,
    )
}

internal fun SwapEntity.toDomain(
    makeEntity: MakeEntity,
    takeEntity: TakeEntity
): Swap {
    val make = makeEntity.toDomain()
    val take = takeEntity.toDomain(make)

    return Swap(
        swapId = swapId,
        take = take,
        timestamp = timestamp,
        swapState = SwapState.fromValue(swapState.toInt()),
        isRead = isRead,
        secret = secret,
        secretHash = secretHash,
        takerSafeTxTime = takerSafeTxTime,
        makerSafeTxTime = makerSafeTxTime,
        takerSafeTx = takerSafeTx,
        makerSafeTx = makerSafeTx,
        takerRedeemTx = takerRedeemTx,
        makerRedeemTx = makerRedeemTx,
        takerRefundTx = takerRefundTx,
        makerRefundTx = makerRefundTx,
    )
}

internal fun Swap.toEntity(): SwapEntity {
    return SwapEntity(
        swapId = swapId,
        timestamp = timestamp,
        swapState = swapState.step.toLong(),
        isRead = isRead,
        makeId = take.make.makeId,
        takeId = take.takeId,
        takerRefundAddressId = null,
        makerRefundAddressId = null,
        takerRedeemAddressId = null,
        makerRedeemAddressId = null,
        secret = secret,
        secretHash = secretHash,
        takerRefundTime = 0,
        makerRefundTime = 0,
        takerSafeTxTime = takerSafeTxTime,
        makerSafeTxTime = makerSafeTxTime,
        takerSafeTx = takerSafeTx,
        makerSafeTx = makerSafeTx,
        takerRedeemTx = takerRedeemTx,
        makerRedeemTx = makerRedeemTx,
        takerRefundTx = takerRefundTx,
        makerRefundTx = makerRefundTx,
        takerSafeAmount = "0",
        makerSafeAmount = "0",
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
        makerTokenDecimal = makerToken.decimal.toLong(),
        takerTokenCoinId = takerToken.coin.id,
        takerTokenCoinSymbol = takerToken.coin.symbol,
        takerTokenCoinName = takerToken.coin.name,
        takerTokenCoinIconUrl = takerToken.coin.iconUrl,
        takerTokenContractAddress = takerToken.contractAddress,
        takerTokenBlockchain = takerToken.blockchain.toStorageName(),
        takerTokenDecimal = takerToken.decimal.toLong()
    )
}

internal fun Take.toTakeEntity(): TakeEntity {
    return TakeEntity(
        takeId = takeId,
        takerId = takerId,
        takerRefundAddress = refundAddress,
        takerRedeemAddress = redeemAddress,
        makerFinalAmount = makerFinalAmount.toPlainString(),
        takerFinalAmount = takerFinalAmount.toPlainString(),
        makeId = make.makeId
    )
}