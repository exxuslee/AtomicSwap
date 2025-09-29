package com.exxlexxlee.atomicswap.domain.di

import com.exxlexxlee.atomicswap.domain.usecases.NotificationReaderUseCase
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.domain.usecases.ThemeController
import org.koin.dsl.module

val domainModule = module {
    single { ThemeController(get()) }
    factory { SettingsUseCase(get()) }
    factory { NotificationReaderUseCase(get()) }
}