package com.exxlexxlee.atomicswap

import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import com.exxlexxlee.atomicswap.core.common.base.BaseApp
import com.exxlexxlee.atomicswap.di.appModule
import com.exxlexxlee.atomicswap.push.NotificationChannels
import com.exxlexxlee.atomicswap.service.AppLifecycleObserver
import com.hwasfy.localize.util.LocaleHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AtomicSwapApp : BaseApp() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.wrapContext(base))
    }

    override fun onCreate() {
        super.onCreate()

        initializeKoin()
        NotificationChannels.registerAll(this)

        val lifecycle = ProcessLifecycleOwner.get().lifecycle
        val observer = AppLifecycleObserver(this)
        lifecycle.addObserver(observer)
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@AtomicSwapApp)
            modules(appModule)
        }
    }

}
