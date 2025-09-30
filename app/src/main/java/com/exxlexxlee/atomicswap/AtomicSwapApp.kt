package com.exxlexxlee.atomicswap

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.exxlexxlee.atomicswap.core.common.base.BaseApp
import com.exxlexxlee.atomicswap.di.appModule
import com.exxlexxlee.atomicswap.push.PushMessagingService
import com.hwasfy.localize.util.LocaleHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AtomicSwapApp : BaseApp() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.wrapContext(base))
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
        startKoin {
            androidLogger()
            androidContext(this@AtomicSwapApp)
            modules(appModule)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = PushMessagingService.NOTIFICATION_CHANNEL_ID
            val name = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
