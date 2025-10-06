package com.exxlexxlee.atomicswap.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationsDao {
    @Query("SELECT COUNT(*) FROM Notifications WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationEntity)

    @Query("UPDATE Notifications SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)

    @Query("SELECT * FROM Notifications ORDER BY createdAt DESC")
    suspend fun getAll(): List<NotificationEntity>

    @Query("DELETE FROM Notifications WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM Notifications")
    suspend fun deleteAll()

    @Query("SELECT * FROM Notifications ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getPaged(limit: Int, offset: Int): List<NotificationEntity>
}

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


