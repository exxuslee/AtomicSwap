package com.example.atomicswap.feature.settings.di

import com.example.atomicswap.feature.settings.SettingsViewModel
import com.example.atomicswap.feature.settings.ThemeController
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    single { ThemeController() }
    viewModel { SettingsViewModel(get()) }
}
