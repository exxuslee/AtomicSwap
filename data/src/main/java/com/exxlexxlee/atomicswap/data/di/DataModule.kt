package com.exxlexxlee.atomicswap.data.di

import com.exxlexxlee.atomicswap.core.database.NotificationsDao
import com.exxlexxlee.atomicswap.data.repository.NotificationsRepositoryFakeImpl
import com.exxlexxlee.atomicswap.data.repository.NotificationsRepositoryImpl
import com.exxlexxlee.atomicswap.data.repository.SettingsRepositoryImpl
import com.exxlexxlee.atomicswap.data.repository.SwapRepositoryFakeImpl
import com.exxlexxlee.atomicswap.data.repository.SwapRepositoryImpl
import com.exxlexxlee.atomicswap.core.database.SwapDao
import com.exxlexxlee.atomicswap.domain.repository.NotificationRepository
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import org.koin.dsl.module

val dataModule = module {
    single<SwapRepository> { SwapRepositoryImpl(get<SwapDao>()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get(), get()) }
    single<NotificationRepository> { NotificationsRepositoryImpl(get<NotificationsDao>()) }
    single<NotificationRepository.Reader> { NotificationsRepositoryImpl(get<NotificationsDao>()) }
}

val fakeDataModule = module {
    single<SwapRepository> { SwapRepositoryFakeImpl() }
    single<SettingsRepository> { SettingsRepositoryImpl(get(), get()) }
    single<NotificationRepository> { NotificationsRepositoryFakeImpl() }
    single<NotificationRepository.Reader> { NotificationsRepositoryFakeImpl() }
}