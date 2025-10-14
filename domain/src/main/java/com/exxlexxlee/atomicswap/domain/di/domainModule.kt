package com.exxlexxlee.atomicswap.domain.di

import com.exxlexxlee.atomicswap.domain.usecases.AggregatorUseCase
import com.exxlexxlee.atomicswap.domain.usecases.MakeUseCase
import com.exxlexxlee.atomicswap.domain.usecases.PushReaderUseCase
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.domain.usecases.ThemeController
import com.exxlexxlee.atomicswap.domain.usecases.TokensUseCase
import org.koin.dsl.module

val domainModule = module {
    single<ThemeController> { ThemeController.Base(get()) }
    single<SettingsUseCase> { SettingsUseCase.Base(get()) }
    single<PushReaderUseCase> { PushReaderUseCase.Base(get()) }
    single<AggregatorUseCase> { AggregatorUseCase.Base(get()) }
    single<SwapUseCase> { SwapUseCase.Base(get()) }
    single<MakeUseCase> { MakeUseCase.Base(get()) }
    single< TokensUseCase> { TokensUseCase.Base(get()) }
}