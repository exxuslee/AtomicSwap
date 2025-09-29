package com.exxlexxlee.atomicswap.core.common.base

import android.app.Application
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManagerImpl

abstract class CoreApp : Application() {
    private val walletConnectManager: WalletConnectManager by lazy { WalletConnectManagerImpl() }

    override fun onCreate() {
        super.onCreate()
        walletConnectManager.initializeReownCore(this)
    }

}

