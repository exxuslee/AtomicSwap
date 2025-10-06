package com.exxlexxlee.atomicswap.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
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