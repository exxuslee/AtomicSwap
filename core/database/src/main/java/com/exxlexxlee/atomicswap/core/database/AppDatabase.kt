package com.exxlexxlee.atomicswap.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        NotificationEntity::class,
        MakeEntity::class,
        TakeEntity::class,
        SwapEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationsDao(): NotificationsDao
    abstract fun swapDao(): SwapDao
}


