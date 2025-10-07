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
interface MakeDao {
    @Query("SELECT * FROM MakeEntity ORDER BY timestamp DESC")
    fun makeAll(): Flow<List<MakeEntity>>

    @Query("SELECT * FROM MakeEntity WHERE makeId = :makeId")
    fun make(makeId: String): MakeEntity

    @Query("SELECT * FROM MakeEntity WHERE makerId = :makerId")
    fun myMake(makerId: String): List<MakeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMake(entity: MakeEntity)

}


