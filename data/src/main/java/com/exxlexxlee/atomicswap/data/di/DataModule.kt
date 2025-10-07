package com.exxlexxlee.atomicswap.data.di

import com.exxlexxlee.atomicswap.core.database.MakeDao
import com.exxlexxlee.atomicswap.core.database.PushDao
import com.exxlexxlee.atomicswap.core.database.SwapDao
import com.exxlexxlee.atomicswap.core.database.TakeDao
import com.exxlexxlee.atomicswap.data.repository.MakeRepositoryFakeImpl
import com.exxlexxlee.atomicswap.data.repository.MakeRepositoryImpl
import com.exxlexxlee.atomicswap.data.repository.PushRepositoryFakeImpl
import com.exxlexxlee.atomicswap.data.repository.PushRepositoryImpl
import com.exxlexxlee.atomicswap.data.repository.SettingsRepositoryImpl
import com.exxlexxlee.atomicswap.data.repository.SwapRepositoryFakeImpl
import com.exxlexxlee.atomicswap.data.repository.SwapRepositoryImpl
import com.exxlexxlee.atomicswap.domain.repository.MakeRepository
import com.exxlexxlee.atomicswap.domain.repository.PushRepository
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import org.koin.dsl.module

val dataModule = module {
    single<SwapRepository> {
        SwapRepositoryImpl(get<SwapDao>(), get<MakeDao>(), get<TakeDao>())
    }
    single<MakeRepository> { MakeRepositoryImpl(get<MakeDao>()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get(), get()) }
    single<PushRepository> { PushRepositoryImpl(get<PushDao>()) }
    single<PushRepository.Reader> { PushRepositoryImpl(get<PushDao>()) }
}

val fakeDataModule = module {
    single<SwapRepository> { SwapRepositoryFakeImpl() }
    single<MakeRepository> { MakeRepositoryFakeImpl() }
    single<SettingsRepository> { SettingsRepositoryImpl(get(), get()) }
    single<PushRepository> { PushRepositoryFakeImpl() }
    single<PushRepository.Reader> { PushRepositoryFakeImpl() }
}