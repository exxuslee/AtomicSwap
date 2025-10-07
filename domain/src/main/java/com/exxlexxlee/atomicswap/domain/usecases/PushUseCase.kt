package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.repository.PushRepository

interface PushUseCase {

    suspend fun save(item: Notification)

    class Base(
        private val pushRepository: PushRepository,
    ) : PushUseCase {

        override suspend fun save(item: Notification) = pushRepository.save(item)

    }

}