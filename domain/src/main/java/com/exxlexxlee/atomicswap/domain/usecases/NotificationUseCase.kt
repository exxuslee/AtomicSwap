package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.NotificationRepository

class NotificationUseCase(
    private val notificationRepository: NotificationRepository,
) {

    fun save(item: Notification) = notificationRepository.save(item)

}