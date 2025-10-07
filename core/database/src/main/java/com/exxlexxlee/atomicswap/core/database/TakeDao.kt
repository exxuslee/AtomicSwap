package com.exxlexxlee.atomicswap.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exxlexxlee.atomicswap.core.database.model.TakeEntity

@Dao
interface TakeDao {

    @Query("SELECT * FROM TakeEntity WHERE takeId = :takeId")
    suspend fun take(takeId: String): TakeEntity

    @Query("SELECT * FROM TakeEntity WHERE makeId = :makeId")
    suspend fun selectTakesByMakeId(makeId: String): List<TakeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTake(entity: TakeEntity)

}


