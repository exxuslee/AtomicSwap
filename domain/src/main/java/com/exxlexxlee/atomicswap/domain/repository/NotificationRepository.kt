package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun save(notification: Notification)

    interface Reader : NotificationRepository {

        val unreadCount: Flow<Int>

        suspend fun markAsRead(id: Long)

        suspend fun all(): List<Notification>

        suspend fun delete(id: Long)
        suspend fun deleteAll()

        suspend fun notificationsPaged(page: Int, pageSize: Int): List<Notification>
    }

}