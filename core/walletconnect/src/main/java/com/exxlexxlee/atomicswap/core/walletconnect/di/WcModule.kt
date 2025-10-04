package com.exxlexxlee.atomicswap.core.walletconnect.di

import com.exxlexxlee.atomicswap.core.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.core.walletconnect.WalletConnectManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val wcModule = module {
    single<WalletConnectManager> { WalletConnectManagerImpl(androidContext() ) }
}