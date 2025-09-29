package com.exxlexxlee.atomicswap.core.common.walletconnect

import kotlinx.coroutines.flow.StateFlow

interface WalletConnectManager {
    val isConnected: StateFlow<Boolean>
    suspend fun connect()
    suspend fun disconnect()
}


