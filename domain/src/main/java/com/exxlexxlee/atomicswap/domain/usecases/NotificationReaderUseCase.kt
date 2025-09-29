package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

interface NotificationReaderUseCase {
    val unreadCount: Flow<Int>
    fun save(item: Notification)
    suspend fun markAsRead(id: Long)
    suspend fun all(): List<Notification>
    suspend fun delete(id: Long)
    suspend fun deleteAll()
    suspend fun notificationsPaged(page: Int, pageSize: Int): List<Notification>

    class Base(private val notificationRepository: NotificationRepository.Reader) :
        NotificationReaderUseCase {

        override val unreadCount = notificationRepository.unreadCount

        override fun save(item: Notification) = notificationRepository.save(item)

        override suspend fun markAsRead(id: Long) = notificationRepository.markAsRead(id)

        override suspend fun all(): List<Notification> = notificationRepository.all()

        override suspend fun delete(id: Long) = notificationRepository.delete(id)
        override suspend fun deleteAll() = notificationRepository.deleteAll()

        override suspend fun notificationsPaged(page: Int, pageSize: Int): List<Notification> =
            notificationRepository.notificationsPaged(page, pageSize)
    }
}