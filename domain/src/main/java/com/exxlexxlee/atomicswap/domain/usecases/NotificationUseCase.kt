package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.NotificationRepository

interface NotificationUseCase {

    suspend fun save(item: Notification)

    class Base(
        private val notificationRepository: NotificationRepository,
    ) : NotificationUseCase {

        override suspend fun save(item: Notification) = notificationRepository.save(item)

    }
}