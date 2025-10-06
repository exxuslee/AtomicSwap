package com.exxlexxlee.atomicswap.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.exxlexxlee.atomicswap.core.swap.model.Make
import java.math.BigDecimal

@Entity(
    tableName = "TakeEntity",
    foreignKeys = [
        ForeignKey(
            entity = MakeEntity::class,
            parentColumns = ["makeId"],
            childColumns = ["makeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["makeId"])]
)
data class TakeEntity(
    @PrimaryKey
    @ColumnInfo(name = "takeId") val takeId: String,
    @ColumnInfo(name = "makeId") val makeId: String,
    @ColumnInfo(name = "takerId") val takerId: String,
    @ColumnInfo(name = "redeemAddress") val redeemAddress: String,
    @ColumnInfo(name = "refundAddress") val refundAddress: String,
    @ColumnInfo(name = "isConfirmed") val isConfirmed: Boolean,
    @ColumnInfo(name = "makerFinalAmount") val makerFinalAmount: BigDecimal,
    @ColumnInfo(name = "takerFinalAmount") val takerFinalAmount: BigDecimal,
    @ColumnInfo(name = "takerSafeAmount") val takerSafeAmount: BigDecimal,
    @ColumnInfo(name = "makerSafeAmount") val makerSafeAmount: BigDecimal,
)


