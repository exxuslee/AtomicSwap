package com.exxlexxlee.atomicswap.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MakeEntity")
data class MakeEntity(
    @PrimaryKey val makeId: String,
    val makerId: String,
    val makerRefundAddress: String,
    val makerRedeemAddress: String,
    val makerExactAmount: String,
    val takerExactAmount: String,
    val makerStartAmount: String,
    val takerStartAmount: String,

    // Maker Token
    val makerTokenCoinId: String,
    val makerTokenCoinSymbol: String,
    val makerTokenCoinName: String,
    val makerTokenCoinIconUrl: String,
    val makerTokenContractAddress: String?,
    val makerTokenBlockchain: String,
    val makerTokenDecimal: Int,

    // Taker Token
    val takerTokenCoinId: String,
    val takerTokenCoinSymbol: String,
    val takerTokenCoinName: String,
    val takerTokenCoinIconUrl: String,
    val takerTokenContractAddress: String?,
    val takerTokenBlockchain: String,
    val takerTokenDecimal: Int,
)