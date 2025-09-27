package com.example.atomicswap.data.repository

import com.example.atomicswap.core.database.AppDatabase
import com.example.atomicswap.domain.model.Notification
import com.example.atomicswap.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import app.cash.sqldelight.coroutines.asFlow


class NotificationsRepositoryImpl(
    private val db: AppDatabase,
) : NotificationRepository.Reader {

    override val unreadCount: Flow<Int> =
        db.notificationsQueries.getUnreadCount().asFlow().map { it.executeAsOne().toInt() }

    override fun save(notification: Notification) {
        db.notificationsQueries.insertNotification(
            showInForeground = if (notification.showInForeground) 1 else 0,
            isRead = if (notification.isRead) 1 else 0,
            title = notification.title,
            body = notification.body,
            groupCode = notification.groupCode.toLong(),
            createdAt = System.currentTimeMillis()
        )
    }

    override suspend fun markAsRead(id: Long) {
        db.notificationsQueries.markAsRead(id)
    }

    override suspend fun all(): List<Notification> {
        return db.notificationsQueries.getAllNotifications().executeAsList().map { notificationEntity ->
            Notification(
                id = notificationEntity.id,
                showInForeground = notificationEntity.showInForeground == 1L,
                isRead = notificationEntity.isRead == 1L,
                title = notificationEntity.title,
                body = notificationEntity.body,
                groupCode = notificationEntity.groupCode.toInt()
            )
        }
    }

    override suspend fun delete(id: Long) {
        db.notificationsQueries.deleteNotification(id)
    }

    override suspend fun notificationsPaged(
        page: Int,
        pageSize: Int
    ): List<Notification> {
        val offset = page * pageSize
        return db.notificationsQueries
            .getNotificationsPaged(pageSize.toLong(), offset.toLong())
            .executeAsList()
            .map { notificationEntity ->
                Notification(
                    id = notificationEntity.id,
                    showInForeground = notificationEntity.showInForeground == 1L,
                    isRead = notificationEntity.isRead == 1L,
                    title = notificationEntity.title,
                    body = notificationEntity.body,
                    groupCode = notificationEntity.groupCode.toInt()
                )
            }
    }


}