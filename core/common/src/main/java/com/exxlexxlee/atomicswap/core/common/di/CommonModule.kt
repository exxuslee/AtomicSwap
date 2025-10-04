package com.exxlexxlee.atomicswap.core.common.di

import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    single<WalletConnectManager> { WalletConnectManagerImpl(androidContext() ) }
}