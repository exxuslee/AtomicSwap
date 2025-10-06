package com.exxlexxlee.atomicswap.core.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "showInForeground") val showInForeground: Boolean,
    @ColumnInfo(name = "isRead") val isRead: Boolean,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "groupCode") val groupCode: Int,
    @ColumnInfo(name = "createdAt") val createdAt: Long,
)

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

@Entity(
    tableName = "TakeEntity",
    foreignKeys = [
        ForeignKey(
            entity = MakeEntity::class,
            parentColumns = ["makeId"],
            childColumns = ["makeId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["makeId"])],
)
data class TakeEntity(
    @PrimaryKey val takeId: String,
    val takerId: String,
    val takerRefundAddress: String,
    val takerRedeemAddress: String,
    val makerFinalAmount: String,
    val takerFinalAmount: String,
    val makeId: String,
)

@Entity(
    tableName = "SwapEntity",
    foreignKeys = [
        ForeignKey(
            entity = MakeEntity::class,
            parentColumns = ["makeId"],
            childColumns = ["makeId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["makeId"])],
)
data class SwapEntity(
    @PrimaryKey val swapId: String,
    val timestamp: Long,
    val swapState: Int,
    val isRead: Boolean,

    val makeId: String,
    val takeId: String?,

    val takerRefundAddressId: String?,
    val makerRefundAddressId: String?,
    val takerRedeemAddressId: String?,
    val makerRedeemAddressId: String?,

    val secret: String?,
    val secretHash: String,

    val takerRefundTime: Long,
    val makerRefundTime: Long,
    val takerSafeTxTime: Long?,
    val makerSafeTxTime: Long?,

    val takerSafeTx: String?,
    val makerSafeTx: String?,
    val takerRedeemTx: String?,
    val makerRedeemTx: String?,
    val takerRefundTx: String?,
    val makerRefundTx: String?,

    val takerSafeAmount: String,
    val makerSafeAmount: String,
)


