package com.exxlexxlee.atomicswap

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner

import com.exxlexxlee.atomicswap.core.common.base.BaseApp
import com.exxlexxlee.atomicswap.di.appModule
import com.exxlexxlee.atomicswap.push.PushMessagingService
import com.exxlexxlee.atomicswap.service.AppLifecycleObserver
import com.hwasfy.localize.util.LocaleHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class AtomicSwapApp : BaseApp() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.wrapContext(base))
    }

    override fun onCreate() {
        super.onCreate()

        initializeKoin()
        createNotificationChannels()

        ProcessLifecycleOwner.get().lifecycle.addObserver(
            AppLifecycleObserver(this)
        )
    }

    private fun initializeKoin() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@AtomicSwapApp)
            modules(appModule)
        }
    }

    private fun createNotificationChannels() {
        val channels = listOf(
            createPushNotificationChannel(),
            createServiceNotificationChannel()
        )

        val manager = getSystemService(NotificationManager::class.java)
        channels.forEach { channel ->
            manager.createNotificationChannel(channel)
        }

        Timber.d("Notification channels created")
    }

    private fun createPushNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            PushMessagingService.CHANNEL_ID,
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.notification_channel_description)
            enableVibration(true)
            setShowBadge(true)
        }
    }

    private fun createServiceNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            SERVICE_CHANNEL_ID,
            getString(R.string.service_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.service_channel_description)
            setShowBadge(false)
        }
    }

    companion object {
        private const val SERVICE_CHANNEL_ID = "background_service_channel"
    }
}
