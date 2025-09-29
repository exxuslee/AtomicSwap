package com.exxlexxlee.atomicswap.core.common.base

import android.app.Application
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManagerImpl

abstract class CoreApp : Application() {
    override fun onCreate() {
        super.onCreate()
        WalletConnectManagerImpl().initializeReownCore(this)
    }

}

