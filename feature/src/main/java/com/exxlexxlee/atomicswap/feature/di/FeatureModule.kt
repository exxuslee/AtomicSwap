package com.exxlexxlee.atomicswap.feature.di

import com.exxlexxlee.atomicswap.core.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.feature.common.swap.SwapViewModel
import com.exxlexxlee.atomicswap.feature.common.tokens.TokensViewModel
import com.exxlexxlee.atomicswap.feature.root.MainViewModel
import com.exxlexxlee.atomicswap.feature.tabs.book.main.BookViewModel
import com.exxlexxlee.atomicswap.feature.tabs.book.make.MakeViewModel
import com.exxlexxlee.atomicswap.feature.tabs.book.my.MyMakeViewModel
import com.exxlexxlee.atomicswap.feature.tabs.book.subscribe.SubscribeViewModel
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.ActiveViewModel
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.ChronicleViewModel
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.complete.CompleteViewModel
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.all.AllChronicleViewModel
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded.RefundedViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.aggregator.AggregatorViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.DonateViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.language.LanguageViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.main.SettingsViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.notification.NotificationViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.scanner.ScannerViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.terms.TermsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel { MainViewModel(get(), get(), get()) }

    viewModel { (swapId: String) -> SwapViewModel(swapId, get()) }
    viewModel { TokensViewModel(get()) }

    viewModel { BookViewModel(get()) }
    viewModel { MakeViewModel(get()) }
    viewModel { MyMakeViewModel() }
    viewModel { SubscribeViewModel() }

    viewModel { ChronicleViewModel(get() ) }
    viewModel { AllChronicleViewModel(get()) }
    viewModel { ActiveViewModel(get()) }
    viewModel { CompleteViewModel(get()) }
    viewModel { RefundedViewModel(get()) }

    viewModel { SettingsViewModel(get(), get(), get(), get(), get<WalletConnectManager>()) }
    viewModel { DonateViewModel() }
    viewModel { LanguageViewModel() }
    viewModel { NotificationViewModel(get()) }
    viewModel { AggregatorViewModel(get()) }
    viewModel { TermsViewModel(get()) }
    viewModel { ScannerViewModel() }

}
