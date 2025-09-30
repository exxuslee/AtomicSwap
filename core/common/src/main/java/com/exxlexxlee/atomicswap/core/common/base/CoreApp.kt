package com.exxlexxlee.atomicswap.core.common.base

import android.app.Application
import com.exxlexxlee.atomicswap.core.common.BuildConfig
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManagerImpl
import timber.log.Timber

abstract class CoreApp : Application() {

    private val walletConnectManager: WalletConnectManager by lazy { WalletConnectManagerImpl() }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        walletConnectManager.initializeReownCore(this)
    }

}

