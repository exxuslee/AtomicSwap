package com.example.atomicswap.data.repository

import com.example.atomicswap.core.database.AppDatabase
import com.example.atomicswap.domain.model.Notification
import com.example.atomicswap.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class NotificationsRepositoryImpl(
    private val db: AppDatabase,
) : NotificationRepository.Reader {

    override val unreadCount: Flow<Int> = flowOf()

    override fun save(notification: Notification) {
        TODO("Not yet implemented")
    }

    override suspend fun markAsRead(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun all(): List<Notification> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun notificationsPaged(
        page: Int,
        pageSize: Int
    ): List<Notification> {
        TODO("Not yet implemented")
    }


}