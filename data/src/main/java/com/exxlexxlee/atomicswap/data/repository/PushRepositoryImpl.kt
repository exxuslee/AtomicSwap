package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.database.PushDao
import com.exxlexxlee.atomicswap.core.database.model.PushEntity
import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.PushRepository
import kotlinx.coroutines.flow.Flow


class PushRepositoryImpl(
    private val dao: PushDao,
) : PushRepository.Reader {

    override val unreadCount: Flow<Int> =
        dao.getUnreadCount()

    override suspend fun save(notification: Notification) {
        dao.insert(
            PushEntity(
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