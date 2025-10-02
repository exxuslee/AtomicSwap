package com.exxlexxlee.atomicswap.feature.chronicle.di

import com.exxlexxlee.atomicswap.feature.chronicle.main.ChronicleViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val chronicleModule = module {
    viewModel { ChronicleViewModel(get(), get() ) }
}
