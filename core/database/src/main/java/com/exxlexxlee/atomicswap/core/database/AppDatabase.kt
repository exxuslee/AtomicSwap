package com.exxlexxlee.atomicswap.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exxlexxlee.atomicswap.core.database.converter.Converters
import com.exxlexxlee.atomicswap.core.database.model.MakeEntity
import com.exxlexxlee.atomicswap.core.database.model.NotificationEntity
import com.exxlexxlee.atomicswap.core.database.model.SwapEntity
import com.exxlexxlee.atomicswap.core.database.model.TakeEntity

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
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notificationsDao(): NotificationsDao
    abstract fun swapDao(): SwapDao
}


