package com.exxlexxlee.atomicswap.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.exxlexxlee.atomicswap.core.swap.model.AmountType
import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.core.swap.model.Token
import java.math.BigDecimal

@Entity(tableName = "MakeEntity")
data class MakeEntity(
    @PrimaryKey
    @ColumnInfo(name = "makeId") val makeId: String,
    @ColumnInfo(name = "makerId") val makerId: String,
    @ColumnInfo(name = "makerToken") val makerToken: Token,
    @ColumnInfo(name = "takerToken") val takerToken: Token,
    @ColumnInfo(name = "refundAddress") val refundAddress: String,
    @ColumnInfo(name = "redeemAddress") val redeemAddress: String,
    @ColumnInfo(name = "amount") val amount: AmountType,
    @ColumnInfo(name = "priceType") val priceType: PriceType,
    @ColumnInfo(name = "isOn") val isOn: Boolean,
    @ColumnInfo(name = "reservedAmount") val reservedAmount: BigDecimal,
    @ColumnInfo(name = "refundTime") val refundTime: Long,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
)


