package com.exxlexxlee.atomicswap.core.common.di

import android.app.Application
import com.exxlexxlee.atomicswap.core.common.base.CoreApp
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManagerImpl
import org.koin.dsl.module

val coreAppModule = module {
    single<Application> { get<CoreApp>() }
    single<WalletConnectManager> { WalletConnectManagerImpl() }
}