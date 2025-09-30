package com.exxlexxlee.atomicswap.core.common.walletconnect

import com.exxlexxlee.atomicswap.core.common.base.CoreApp
import com.reown.android.Core
import com.reown.android.CoreClient
import com.reown.android.relay.ConnectionType
import com.reown.appkit.client.AppKit
import com.reown.appkit.client.Modal
import com.reown.appkit.presets.AppKitChainsPresets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class WalletConnectManagerImpl : WalletConnectManager {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean> = _isConnected

    override suspend fun connect() {
        _isConnected.value = true
        Timber.i("WalletConnect: connected")
    }

    override suspend fun disconnect() {
        _isConnected.value = false
        Timber.i("WalletConnect: disconnected")
    }

    override fun initializeReownCore(app: CoreApp) {
        runCatching {
            val appMetaData = Core.Model.AppMetaData(
                name = "Histopia",
                description = "Histopia Wallet Integration",
                url = AppConfig.APP_URL,
                icons = listOf(AppConfig.APP_URL),
                redirect = AppConfig.REDIRECT_URL
            )
            CoreClient.initialize(
                application = app,
                projectId = AppConfig.PROJECT_ID,
                metaData = appMetaData,
                connectionType = ConnectionType.AUTOMATIC,
                onError = { error -> Timber.e("CoreClient init error: $error") }
            )

            AppKit.setChains(AppKitChainsPresets.ethChains.values.toList())
            AppKit.setDelegate(DappDelegate)

            val initParams = Modal.Params.Init(CoreClient)
            AppKit.initialize(
                init = initParams,
                onSuccess = {
                    Timber.i("AppKit initialized successfully")
                },
                onError = { error ->
                    Timber.e("AppKit init error: $error")
                }
            )
        }.onFailure { e ->
            Timber.e(e, "WalletConnect initialization failed")
        }
    }

}


