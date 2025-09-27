package com.example.atomicswap.data.repository

import com.example.atomicswap.domain.model.Notification
import com.example.atomicswap.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNotificationsRepositoryImpl : NotificationRepository.Reader {

    // Mock data for testing
    private val mockNotifications = mutableListOf(
        Notification(
            id = 1,
            showInForeground = true,
            isRead = false,
            title = "Swap Completed",
            body = "Your atomic swap has been completed successfully",
            groupCode = 1
        ),
        Notification(
            id = 2,
            showInForeground = false,
            isRead = true,
            title = "New Offer Available",
            body = "A new swap offer is available in your area",
            groupCode = 2
        ),
        Notification(
            id = 3,
            showInForeground = true,
            isRead = false,
            title = "Payment Received",
            body = "You have received payment for your swap",
            groupCode = 1
        ),
        Notification(
            id = 4,
            showInForeground = false,
            isRead = false,
            title = "Swap Expired",
            body = "Your swap offer has expired",
            groupCode = 3
        ),
        Notification(
            id = 5,
            showInForeground = true,
            isRead = true,
            title = "New Message",
            body = "You have received a new message from a trader",
            groupCode = 2
        )
    )

    override val unreadCount: Flow<Int> = 
        flowOf(mockNotifications.count { !it.isRead })

    override fun save(notification: Notification) {
        mockNotifications.add(notification)
    }

    override suspend fun markAsRead(id: Long) {
        mockNotifications.find { it.id == id }?.let { notification ->
            val index = mockNotifications.indexOf(notification)
            mockNotifications[index] = notification.copy(isRead = true)
        }
    }

    override suspend fun all(): List<Notification> {
        return mockNotifications.sortedByDescending { it.id }
    }

    override suspend fun delete(id: Long) {
        mockNotifications.removeAll { it.id == id }
    }

    override suspend fun notificationsPaged(
        page: Int,
        pageSize: Int
    ): List<Notification> {
        val offset = page * pageSize
        return mockNotifications.sortedByDescending { it.id }
            .drop(offset)
            .take(pageSize)
    }
}
