package com.example.atomicswap.data.di

import com.example.atomicswap.core.database.AppDatabase
import com.example.atomicswap.data.repository.NotificationsRepositoryImpl
import com.example.atomicswap.data.repository.SettingsRepositoryImpl
import com.example.atomicswap.data.repository.SqlDelightSwapRepository
import com.example.atomicswap.domain.repository.NotificationRepository
import com.example.atomicswap.domain.repository.SettingsRepository
import com.example.atomicswap.domain.repository.SwapRepository
import org.koin.dsl.module


val dataModule = module {
    single { AppDatabase(get()) }
    single<SwapRepository> { SqlDelightSwapRepository(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single<NotificationsRepositoryImpl> { NotificationsRepositoryImpl(get()) }
    single<NotificationRepository> { get<NotificationsRepositoryImpl>() }
    single<NotificationRepository.Reader> { get<NotificationsRepositoryImpl>() }
}