package com.example.atomicswap

import android.content.Context
import com.example.atomicswap.core.database.databaseModule
import com.example.atomicswap.core.network.networkModule
import com.example.atomicswap.core.ui.base.CoreApp
import com.example.atomicswap.core.ui.di.coreAppModule
import com.example.atomicswap.data.di.dataModule
import com.example.atomicswap.feature.history.historyModule
import com.example.atomicswap.feature.maker.makerModule
import com.example.atomicswap.feature.settings.di.settingsModule
import com.example.atomicswap.feature.taker.takerModule
import com.hwasfy.localize.util.LocaleHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AtomicSwapApp : CoreApp() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.wrapContext(base))
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AtomicSwapApp)
            modules(
                coreAppModule,
                networkModule,
                databaseModule,
                dataModule,
                takerModule,
                makerModule,
                historyModule,
                settingsModule
            )
        }
    }
}
