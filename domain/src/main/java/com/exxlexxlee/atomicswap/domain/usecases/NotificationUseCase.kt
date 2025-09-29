package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.NotificationRepository

interface NotificationUseCase {

    fun save(item: Notification)

    class Base(
        private val notificationRepository: NotificationRepository,
    ) : NotificationUseCase {

        override fun save(item: Notification) = notificationRepository.save(item)

    }
}