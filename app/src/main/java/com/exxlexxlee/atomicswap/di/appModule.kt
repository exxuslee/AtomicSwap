package com.exxlexxlee.atomicswap.di

import com.exxlexxlee.atomicswap.BuildConfig
import com.exxlexxlee.atomicswap.core.common.di.coreAppModule
import com.exxlexxlee.atomicswap.core.database.databaseModule
import com.exxlexxlee.atomicswap.core.network.networkModule
import com.exxlexxlee.atomicswap.data.di.dataModule
import com.exxlexxlee.atomicswap.data.di.fakeDataModule
import com.exxlexxlee.atomicswap.domain.di.domainModule
import com.exxlexxlee.atomicswap.feature.chronicle.di.chronicleModule
import com.exxlexxlee.atomicswap.feature.maker.makerModule
import com.exxlexxlee.atomicswap.feature.root.di.rootModule
import com.exxlexxlee.atomicswap.feature.settings.di.settingsModule
import com.exxlexxlee.atomicswap.feature.taker.takerModule
import org.koin.dsl.module

val appModule = module {
    includes(coreAppModule)
    includes(domainModule)
    includes(networkModule)
    includes(databaseModule)
    includes(rootModule)
    includes(takerModule)
    includes(makerModule)
    includes(chronicleModule)
    includes(settingsModule)

    if (BuildConfig.BUILD_TYPE == "mock") {
        includes(fakeDataModule)
    } else {
        includes(dataModule)
    }
}