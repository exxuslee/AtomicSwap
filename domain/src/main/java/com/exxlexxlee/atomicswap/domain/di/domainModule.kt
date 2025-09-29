package com.exxlexxlee.atomicswap.domain.di

import com.exxlexxlee.atomicswap.domain.usecases.NotificationReaderUseCase
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.domain.usecases.ThemeController
import org.koin.dsl.module

val domainModule = module {
    single<ThemeController> { ThemeController.Base(get()) }
    factory<SettingsUseCase> { SettingsUseCase.Base(get()) }
    factory<NotificationReaderUseCase> { NotificationReaderUseCase.Base(get()) }
}