package com.exxlexxlee.atomicswap.feature.settings.di

import com.exxlexxlee.atomicswap.feature.settings.main.SettingsViewModel
import com.exxlexxlee.atomicswap.feature.settings.donate.DonateViewModel
import com.exxlexxlee.atomicswap.feature.settings.language.LanguageViewModel
import com.exxlexxlee.atomicswap.feature.settings.notification.NotificationViewModel
import org.koin.core.module.dsl.viewModel
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManager
import org.koin.dsl.module

val settingsModule = module {
    viewModel { DonateViewModel() }
    viewModel { LanguageViewModel() }
    viewModel { NotificationViewModel(get()) }
    viewModel { SettingsViewModel(get(), get(), get<WalletConnectManager>()) }
}
