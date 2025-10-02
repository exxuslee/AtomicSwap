package com.exxlexxlee.atomicswap.feature.root.di

import com.exxlexxlee.atomicswap.feature.root.MainViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {
    viewModel { MainViewModel(get(), get()) }
}
