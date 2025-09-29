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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

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

    fun initializeReownCore(app: CoreApp) {
        try {
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
                onError = { error -> Timber.e("Initialization error: $error") }

            )
            AppKit.setChains(AppKitChainsPresets.ethChains.values.toList())

            val init = Modal.Params.Init(CoreClient)
            AppKit.initialize(
                init = init,
                onSuccess = {
                    // You might want to handle this differently in Application class
                    // Maybe through a shared preference or another mechanism
                },
                onError = { error ->
                    Timber.e("Initialization error: $error")

                }
            )


        } catch (e: Exception) {
            print(e.toString())
        }
    }

}


