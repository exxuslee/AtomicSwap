package com.exxlexxlee.atomicswap.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exxlexxlee.atomicswap.core.database.converter.Converters
import com.exxlexxlee.atomicswap.core.database.model.MakeEntity
import com.exxlexxlee.atomicswap.core.database.model.PushEntity
import com.exxlexxlee.atomicswap.core.database.model.SwapEntity
import com.exxlexxlee.atomicswap.core.database.model.TakeEntity

@Database(
    entities = [
        PushEntity::class,
        MakeEntity::class,
        TakeEntity::class,
        SwapEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pushDao(): PushDao
    abstract fun swapDao(): SwapDao
    abstract fun makeDao(): MakeDao
    abstract fun takeDao(): TakeDao
}


