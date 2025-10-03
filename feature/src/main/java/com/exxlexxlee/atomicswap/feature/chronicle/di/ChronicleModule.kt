package com.exxlexxlee.atomicswap.feature.chronicle.di

import com.exxlexxlee.atomicswap.feature.chronicle.main.ChronicleViewModel
import com.exxlexxlee.atomicswap.feature.chronicle.swap.SwapViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chronicleModule = module {
    viewModel { ChronicleViewModel(get(), get() ) }
    viewModel { (swapId: String) -> SwapViewModel(swapId, get()) }
}
