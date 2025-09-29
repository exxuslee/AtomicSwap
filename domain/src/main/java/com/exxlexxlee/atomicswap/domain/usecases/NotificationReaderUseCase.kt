package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.NotificationRepository

class NotificationReaderUseCase(
    private val notificationRepository: NotificationRepository.Reader,
) {
    val unreadCount = notificationRepository.unreadCount

    fun save(item: Notification) = notificationRepository.save(item)

    suspend fun markAsRead(id: Long) = notificationRepository.markAsRead(id)

    suspend fun all(): List<Notification> = notificationRepository.all()

    suspend fun delete(id: Long) = notificationRepository.delete(id)
    suspend fun deleteAll() = notificationRepository.deleteAll()

    suspend fun notificationsPaged(page: Int, pageSize: Int): List<Notification> =
        notificationRepository.notificationsPaged(page, pageSize)
}