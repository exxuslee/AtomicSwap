package com.example.atomicswap

import android.content.Context
import com.example.atomicswap.core.common.base.CoreApp
import com.example.atomicswap.di.appModule
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
            modules(appModule)
        }
    }
}
