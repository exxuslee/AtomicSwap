package com.example.atomicswap.feature.settings.di

import com.example.atomicswap.feature.settings.main.SettingsViewModel
import com.example.atomicswap.feature.settings.donate.DonateViewModel
import com.example.atomicswap.feature.settings.language.LanguageViewModel
import com.example.atomicswap.feature.settings.notification.NotificationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { DonateViewModel() }
    viewModel { LanguageViewModel() }
    viewModel { NotificationViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
