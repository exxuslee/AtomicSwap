package com.example.atomicswap.domain.di

import com.example.atomicswap.domain.usecases.NotificationReaderUseCase
import com.example.atomicswap.domain.usecases.SettingsUseCase
import com.example.atomicswap.domain.usecases.ThemeController
import org.koin.dsl.module

val domainModule = module {
    single { ThemeController(get()) }
    factory { SettingsUseCase(get()) }
    factory { NotificationReaderUseCase(get()) }
}