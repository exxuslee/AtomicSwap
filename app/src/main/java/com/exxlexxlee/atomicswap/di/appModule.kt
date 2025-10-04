package com.exxlexxlee.atomicswap.di

import com.exxlexxlee.atomicswap.BuildConfig
import com.exxlexxlee.atomicswap.core.common.di.commonModule
import com.exxlexxlee.atomicswap.core.database.databaseModule
import com.exxlexxlee.atomicswap.core.network.di.networkModule
import com.exxlexxlee.atomicswap.data.di.dataModule
import com.exxlexxlee.atomicswap.data.di.fakeDataModule
import com.exxlexxlee.atomicswap.domain.di.domainModule
import com.exxlexxlee.atomicswap.feature.di.featureModule
import org.koin.dsl.module

val appModule = module {
    includes(commonModule)
    includes(networkModule)
    includes(databaseModule)
    includes(domainModule)
    includes(featureModule)

    if (BuildConfig.BUILD_TYPE == "mock") {
        includes(fakeDataModule)
    } else {
        includes(dataModule)
    }
}