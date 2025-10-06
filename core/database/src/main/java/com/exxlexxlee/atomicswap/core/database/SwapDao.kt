package com.exxlexxlee.atomicswap.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.exxlexxlee.atomicswap.core.database.model.MakeEntity
import com.exxlexxlee.atomicswap.core.database.model.SwapEntity
import com.exxlexxlee.atomicswap.core.database.model.TakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SwapDao {
    @Query("SELECT * FROM SwapEntity ORDER BY timestamp DESC")
    fun selectAll(): Flow<List<SwapEntity>>

    @Query("SELECT * FROM SwapEntity WHERE swapId = :id")
    suspend fun selectById(id: String): SwapEntity

    @Query("SELECT * FROM MakeEntity WHERE makeId = :id")
    suspend fun selectMakeById(id: String): MakeEntity

    @Query("SELECT * FROM TakeEntity WHERE takeId = :id")
    suspend fun selectTakeById(id: String): TakeEntity

    @Query("SELECT * FROM TakeEntity WHERE makeId = :makeId")
    suspend fun selectTakesByMakeId(makeId: String): List<TakeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMake(entity: MakeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTake(entity: TakeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSwap(entity: SwapEntity)

    @Query("DELETE FROM SwapEntity WHERE swapId = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM SwapEntity WHERE swapState IN (7, 0)")
    suspend fun deleteHistory()

    @Transaction
    suspend fun insertAll(make: MakeEntity, take: TakeEntity, swap: SwapEntity) {
        insertMake(make)
        insertTake(take)
        insertSwap(swap)
    }
}


