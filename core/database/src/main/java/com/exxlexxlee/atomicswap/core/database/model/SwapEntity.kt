package com.exxlexxlee.atomicswap.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.exxlexxlee.atomicswap.core.swap.model.SwapState
// import removed: Take not used here

@Entity(
    tableName = "SwapEntity",
    foreignKeys = [
        ForeignKey(
            entity = TakeEntity::class,
            parentColumns = ["takeId"],
            childColumns = ["takeId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["takeId"])],
)
data class SwapEntity(
    @PrimaryKey
    @ColumnInfo(name = "swapId") val swapId: String,
    @ColumnInfo(name = "takeId") val takeId: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "swapState") val swapState: SwapState,
    @ColumnInfo(name = "isRead") val isRead: Boolean,

    @ColumnInfo(name = "secret") val secret: String? = null,
    @ColumnInfo(name = "secretHash") val secretHash: String,

    @ColumnInfo(name = "takerSafeTxTime") val takerSafeTxTime: Long? = null,
    @ColumnInfo(name = "makerSafeTxTime") val makerSafeTxTime: Long? = null,

    @ColumnInfo(name = "takerSafeTx") val takerSafeTx: String? = null,
    @ColumnInfo(name = "makerSafeTx") val makerSafeTx: String? = null,
    @ColumnInfo(name = "takerRedeemTx") val takerRedeemTx: String? = null,
    @ColumnInfo(name = "makerRedeemTx") val makerRedeemTx: String? = null,
    @ColumnInfo(name = "takerRefundTx") val takerRefundTx: String? = null,
    @ColumnInfo(name = "makerRefundTx") val makerRefundTx: String? = null,
)


