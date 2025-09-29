package com.exxlexxlee.atomicswap.data.di

import com.exxlexxlee.atomicswap.core.database.AppDatabase
import com.exxlexxlee.atomicswap.data.repository.FakeNotificationsRepositoryImpl
import com.exxlexxlee.atomicswap.data.repository.NotificationsRepositoryImpl
import com.exxlexxlee.atomicswap.data.repository.SettingsRepositoryImpl
import com.exxlexxlee.atomicswap.data.repository.SqlDelightSwapRepository
import com.exxlexxlee.atomicswap.domain.repository.NotificationRepository
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import org.koin.dsl.module

val dataModule = module {
    single { AppDatabase(get()) }
    single<SwapRepository> { SqlDelightSwapRepository(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<NotificationRepository> { NotificationsRepositoryImpl(get()) }
    single<NotificationRepository.Reader> { NotificationsRepositoryImpl(get()) }
}

val fakeDataModule = module {
    single { AppDatabase(get()) }
    single<SwapRepository> { SqlDelightSwapRepository(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<NotificationRepository> { FakeNotificationsRepositoryImpl() }
    single<NotificationRepository.Reader> { FakeNotificationsRepositoryImpl() }
}