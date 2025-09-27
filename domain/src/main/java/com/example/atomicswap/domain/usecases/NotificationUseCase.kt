package com.example.atomicswap.domain.usecases

import com.example.atomicswap.domain.model.Notification
import com.example.atomicswap.domain.repository.NotificationRepository

class NotificationUseCase(
    private val notificationRepository: NotificationRepository,
) {

    fun save(item: Notification) = notificationRepository.save(item)

}