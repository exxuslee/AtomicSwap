package com.exxlexxlee.atomicswap.core.common.walletconnect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalletConnectManagerImpl : WalletConnectManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean> = _isConnected

    override suspend fun connect() {
        scope.launch { _isConnected.value = true }.join()
    }

    override suspend fun disconnect() {
        scope.launch { _isConnected.value = false }.join()
    }
}


