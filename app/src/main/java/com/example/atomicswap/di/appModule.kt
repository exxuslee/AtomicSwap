package com.example.atomicswap.di

import com.example.atomicswap.BuildConfig
import com.example.atomicswap.core.common.di.coreAppModule
import com.example.atomicswap.core.database.databaseModule
import com.example.atomicswap.core.network.networkModule
import com.example.atomicswap.data.di.dataModule
import com.example.atomicswap.data.di.fakeDataModule
import com.example.atomicswap.domain.di.domainModule
import com.example.atomicswap.feature.history.historyModule
import com.example.atomicswap.feature.maker.makerModule
import com.example.atomicswap.feature.settings.di.settingsModule
import com.example.atomicswap.feature.taker.takerModule
import org.koin.dsl.module

val appModule = module {
    includes(coreAppModule)
    includes(domainModule)
    includes(networkModule)
    includes(databaseModule)
    includes(takerModule)
    includes(makerModule)
    includes(historyModule)
    includes(settingsModule)

    if (BuildConfig.BUILD_TYPE == "mock") {
        includes(dataModule)
    } else {
        includes(fakeDataModule)
    }
}