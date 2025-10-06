package com.exxlexxlee.atomicswap.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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