package com.exxlexxlee.atomicswap.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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