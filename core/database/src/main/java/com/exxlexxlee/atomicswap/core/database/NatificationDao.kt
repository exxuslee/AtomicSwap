package com.exxlexxlee.atomicswap.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exxlexxlee.atomicswap.core.database.model.NotificationEntity
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


