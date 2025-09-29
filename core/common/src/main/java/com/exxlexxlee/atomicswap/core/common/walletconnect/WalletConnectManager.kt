package com.exxlexxlee.atomicswap.core.common.walletconnect

import com.exxlexxlee.atomicswap.core.common.base.CoreApp
import kotlinx.coroutines.flow.StateFlow

interface WalletConnectManager {
    val isConnected: StateFlow<Boolean>
    suspend fun connect()
    suspend fun disconnect()

    fun initializeReownCore(app: CoreApp)
}


