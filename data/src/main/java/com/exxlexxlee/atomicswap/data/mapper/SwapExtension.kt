package com.exxlexxlee.atomicswap.data.mapper

import com.example.atomicswap.core.database.MakeEntity
import com.example.atomicswap.core.database.SwapEntity
import com.example.atomicswap.core.database.TakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.core.swap.model.Make
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
        id = "btc",
        coin = makeTokenCoin,
        contractAddress = makerTokenContractAddress,
        blockchain = Blockchain.valueOf(makerTokenBlockchain),
        decimal = makerTokenDecimal.toInt()
    )

    val takerToken = Token(
        id = "eth",
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
        makerExactAmount = BigDecimal(makerExactAmount),
        takerExactAmount = BigDecimal(takerExactAmount),
        makerStartAmount = BigDecimal(makerStartAmount),
        takerStartAmount = BigDecimal(takerStartAmount)
    )
}

internal fun TakeEntity.toDomain(make: Make): Take {
    return Take(
        make = make,
        takeId = takeId,
        takerId = takerId,
        takerRefundAddress = takerRefundAddress,
        takerRedeemAddress = takerRedeemAddress,
        makerFinalAmount = BigDecimal(makerFinalAmount),
        takerFinalAmount = BigDecimal(takerFinalAmount)
    )
}

internal fun SwapEntity.toDomain(
    makeEntity: MakeEntity,
    takeEntities: List<TakeEntity>
): Swap {
    val make = makeEntity.toDomain()
    val takeList = takeEntities.map { it.toDomain(make) }

    return Swap(
        take = takeList,
        takeId = takeId,
        make = make,
        swapId = swapId,
        timestamp = timestamp,
        swapState = SwapState.fromValue(swapState.toInt()),
        isRead = isRead,
        secret = secret,
        secretHash = secretHash,
        takerRefundTime = takerRefundTime.toInt(),
        makerRefundTime = makerRefundTime.toInt(),
        takerSafeTxTime = takerSafeTxTime,
        makerSafeTxTime = makerSafeTxTime,
        takerSafeTx = takerSafeTx,
        makerSafeTx = makerSafeTx,
        takerRedeemTx = takerRedeemTx,
        makerRedeemTx = makerRedeemTx,
        takerRefundTx = takerRefundTx,
        makerRefundTx = makerRefundTx,
        takerSafeAmount = BigDecimal(takerSafeAmount),
        makerSafeAmount = BigDecimal(makerSafeAmount)
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
        secret = secret,
        secretHash = secretHash,
        takerRefundTime = takerRefundTime.toLong(),
        makerRefundTime = makerRefundTime.toLong(),
        takerSafeTxTime = takerSafeTxTime,
        makerSafeTxTime = makerSafeTxTime,
        takerSafeTx = takerSafeTx,
        makerSafeTx = makerSafeTx,
        takerRedeemTx = takerRedeemTx,
        makerRedeemTx = makerRedeemTx,
        takerRefundTx = takerRefundTx,
        makerRefundTx = makerRefundTx,
        takerSafeAmount = takerSafeAmount.toPlainString(),
        makerSafeAmount = makerSafeAmount.toPlainString()
    )
}

internal fun Make.toMakeEntity(): MakeEntity {
    return MakeEntity(
        makeId = makeId,
        makerId = makerId,
        makerRefundAddress = makerRefundAddress,
        makerRedeemAddress = makerRedeemAddress,
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