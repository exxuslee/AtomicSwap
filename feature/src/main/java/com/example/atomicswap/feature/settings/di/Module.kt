package com.example.atomicswap.feature.settings.di

import com.example.atomicswap.feature.settings.main.SettingsViewModel
import com.example.atomicswap.domain.usecases.SettingsUseCase
import com.example.atomicswap.domain.usecases.ThemeController
import com.example.atomicswap.feature.settings.language.LanguageViewModel
import com.example.atomicswap.feature.settings.notification.NotificationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModel(get()) }
    viewModel { LanguageViewModel() }
    viewModel { NotificationViewModel(get()) }
}
