package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.PushRepository
import kotlinx.coroutines.flow.Flow

interface PushReaderUseCase {
    val unreadCount: Flow<Int>
    suspend fun save(item: Notification)
    suspend fun markAsRead(id: Long)
    suspend fun all(): List<Notification>
    suspend fun delete(id: Long)
    suspend fun deleteAll()
    suspend fun notificationsPaged(page: Int, pageSize: Int): List<Notification>

    class Base(private val pushRepository: PushRepository.Reader) :
        PushReaderUseCase {

        override val unreadCount = pushRepository.unreadCount

        override suspend fun save(item: Notification) = pushRepository.save(item)

        override suspend fun markAsRead(id: Long) = pushRepository.markAsRead(id)

        override suspend fun all(): List<Notification> = pushRepository.all()

        override suspend fun delete(id: Long) = pushRepository.delete(id)
        override suspend fun deleteAll() = pushRepository.deleteAll()

        override suspend fun notificationsPaged(page: Int, pageSize: Int): List<Notification> =
            pushRepository.notificationsPaged(page, pageSize)
    }
}