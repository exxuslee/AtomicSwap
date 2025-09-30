package com.exxlexxlee.atomicswap.core.common.walletconnect

import android.app.Application
import android.content.Context
import com.reown.android.Core
import com.reown.android.CoreClient
import com.reown.android.relay.ConnectionType
import com.reown.appkit.client.AppKit
import com.reown.appkit.client.Modal
import com.reown.appkit.presets.AppKitChainsPresets
import timber.log.Timber

class WalletConnectManagerImpl(
    app: Context,
) : WalletConnectManager {
    override val delegate by lazy { DappDelegate }

    init {
        initializeReownCore(app)
    }

    private fun initializeReownCore(app: Context) {
        runCatching {
            val appMetaData = Core.Model.AppMetaData(
                name = "Histopia",
                description = "Histopia Wallet Integration",
                url = AppConfig.APP_URL,
                icons = listOf(AppConfig.APP_URL),
                redirect = AppConfig.REDIRECT_URL
            )
            CoreClient.initialize(
                application = app as Application,
                projectId = AppConfig.PROJECT_ID,
                metaData = appMetaData,
                connectionType = ConnectionType.AUTOMATIC,
                onError = { error -> Timber.e("CoreClient init error: $error") }
            )

            AppKit.setChains(AppKitChainsPresets.ethChains.values.toList())

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


