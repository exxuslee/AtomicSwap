package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.network.ConnectionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkRepository(
    private val connectionManager: ConnectionManager
) : ConnectionManager.Listener {
    
    private val _isConnected = MutableStateFlow(connectionManager.isConnected)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()
    
    init {
        connectionManager.listener = this
    }
    
    override fun onConnectionChange() {
        _isConnected.value = connectionManager.isConnected
    }
}