package com.exxlexxlee.atomicswap.feature.di

import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.feature.common.swap.SwapViewModel
import com.exxlexxlee.atomicswap.feature.root.MainViewModel
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.ChronicleViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.aggregator.AggregatorViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.DonateViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.language.LanguageViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.main.SettingsViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.notification.NotificationViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.terms.TermsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { ChronicleViewModel(get(), get() ) }
    viewModel { (swapId: String) -> SwapViewModel(swapId, get()) }
    viewModel { DonateViewModel() }
    viewModel { LanguageViewModel() }
    viewModel { NotificationViewModel(get()) }
    viewModel { AggregatorViewModel(get()) }
    viewModel { SettingsViewModel(get(), get(), get(), get(), get<WalletConnectManager>()) }
    viewModel { TermsViewModel(get()) }
}
