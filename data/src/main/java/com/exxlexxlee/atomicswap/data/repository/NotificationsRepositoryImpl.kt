package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.database.NotificationsDao
import com.exxlexxlee.atomicswap.core.database.NotificationEntity
import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class NotificationsRepositoryImpl(
    private val dao: NotificationsDao,
) : NotificationRepository.Reader {

    override val unreadCount: Flow<Int> =
        dao.getUnreadCount()

    override suspend fun save(notification: Notification) {
        dao.insert(
            NotificationEntity(
                showInForeground = notification.showInForeground,
                isRead = notification.isRead,
                title = notification.title,
                body = notification.body,
                groupCode = notification.groupCode,
                createdAt = System.currentTimeMillis(),
            )
        )
    }

    override suspend fun markAsRead(id: Long) {
        dao.markAsRead(id)
    }

    override suspend fun all(): List<Notification> {
        return dao.getAll().map { notificationEntity ->
            Notification(
                id = notificationEntity.id,
                showInForeground = notificationEntity.showInForeground,
                isRead = notificationEntity.isRead,
                title = notificationEntity.title,
                body = notificationEntity.body,
                groupCode = notificationEntity.groupCode
            )
        }
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun notificationsPaged(
        page: Int,
        pageSize: Int
    ): List<Notification> {
        val offset = page * pageSize
        return dao
            .getPaged(pageSize, offset)
            .map { notificationEntity ->
                Notification(
                    id = notificationEntity.id,
                    showInForeground = notificationEntity.showInForeground,
                    isRead = notificationEntity.isRead,
                    title = notificationEntity.title,
                    body = notificationEntity.body,
                    groupCode = notificationEntity.groupCode
                )
            }
    }


}