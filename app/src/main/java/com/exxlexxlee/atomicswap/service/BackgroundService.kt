package com.exxlexxlee.atomicswap.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.exxlexxlee.atomicswap.MainActivity
import com.exxlexxlee.atomicswap.R
import com.exxlexxlee.atomicswap.push.NotificationChannels
import com.exxlexxlee.atomicswap.push.NotificationChannels.CHANNEL_ID
import com.exxlexxlee.atomicswap.push.NotificationChannels.NOTIFICATION_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Foreground service with Binder support for reliable state checking
 */
class BackgroundService : Service() {

    private val binder = LocalBinder()
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private lateinit var notificationManager: NotificationManager
    private var isAppInForeground = false
    private var periodicTaskJob: Job? = null

    inner class LocalBinder : Binder() {
        fun getService(): BackgroundService = this@BackgroundService
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // Ensure channels are present
        NotificationChannels.registerAll(this)
        ServiceBinder.onServiceCreated()
        Timber.d("BackgroundService created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action ?: return START_STICKY
        Timber.d("onStartCommand: $action ")
        when (action) {
            ACTION_START -> handleStartService()
            ACTION_STOP -> handleStopService()
            ACTION_PUSH -> handlePushNotification(intent)
            ACTION_APP_FOREGROUND -> handleAppForegroundChange(true)
            ACTION_APP_BACKGROUND -> handleAppForegroundChange(false)
            else -> Timber.w("Unknown action received: $action")
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        stopPeriodicTask()
        serviceScope.cancel()
        ServiceBinder.onServiceDestroyed()
        Timber.d("BackgroundService destroyed")
        super.onDestroy()
    }

    private fun handleStartService() {
        val notification = buildNotification(
            title = getString(R.string.service_running_title),
            content = getString(R.string.service_running_content),
            silent = isAppInForeground
        )
        startForeground(NOTIFICATION_ID, notification)
        startPeriodicTask()
        Timber.d("BackgroundService started in foreground (silent: $isAppInForeground)")
    }

    private fun handleStopService() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        Timber.d("BackgroundService stopped")
    }

    private fun handlePushNotification(intent: Intent) {
        val title = intent.getStringExtra(EXTRA_TITLE)
            ?: getString(R.string.push_notification_default_title)
        val body = intent.getStringExtra(EXTRA_BODY)
            ?: getString(R.string.push_notification_default_body)

        updateNotification(title, body)
        processPushAsync(title, body)

        Timber.d("Push notification handled: $title")
    }

    private fun handleAppForegroundChange(isInForeground: Boolean) {
        isAppInForeground = isInForeground

        if (isInForeground) {
            if (periodicTaskJob == null) startPeriodicTask()
        } else {
            val notification = buildNotification(
                title = getString(R.string.service_running_title),
                content = getString(R.string.service_running_content),
                silent = false
            )
            notificationManager.notify(NOTIFICATION_ID, notification)
            Timber.d("App in background - notification visible")
        }
    }

    private fun updateNotification(title: String, body: String) {
        val notification = buildNotification(title, body)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun processPushAsync(title: String, body: String) {
        serviceScope.launch {
            try {
                processPushNotification(title, body)
            } catch (e: Exception) {
                Timber.e(e, "Error processing push notification")
            }
        }
    }

    private suspend fun processPushNotification(title: String, body: String) {
        Timber.d("Processing push notification: $title - $body")
        delay(2000)
        Timber.d("Push notification processed successfully")
    }

    private fun startPeriodicTask() {
        stopPeriodicTask()

        periodicTaskJob = serviceScope.launch {
            while (true) {
                try {
                    Timber.d("⏰ Periodic task executed1 - Service ${this@BackgroundService.hashCode()}")
                    delay(60000)
                } catch (e: Exception) {
                    Timber.e(e, "Error in periodic task")
                }
            }
        }

        Timber.d("Periodic task started (interval: 5 seconds)")
    }

    private fun stopPeriodicTask() {
        periodicTaskJob?.cancel()
        periodicTaskJob = null
        Timber.d("Periodic task stopped")
    }

    private fun buildNotification(title: String, content: String, silent: Boolean = false): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)

        if (silent) {
            builder
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setShowWhen(false)
        } else {
            builder
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        }

        return builder.build()
    }

    // Channel creation centralized in NotificationChannels

    companion object {
        private const val ACTION_START = "com.exxlexxlee.atomicswap.ACTION_START"
        private const val ACTION_STOP = "com.exxlexxlee.atomicswap.ACTION_STOP"
        private const val ACTION_PUSH = "com.exxlexxlee.atomicswap.ACTION_PUSH"
        private const val ACTION_APP_FOREGROUND = "com.exxlexxlee.atomicswap.ACTION_APP_FOREGROUND"
        private const val ACTION_APP_BACKGROUND = "com.exxlexxlee.atomicswap.ACTION_APP_BACKGROUND"

        private const val EXTRA_TITLE = "extra_title"
        private const val EXTRA_BODY = "extra_body"

        fun createStartIntent(context: Context): Intent {
            return Intent(context, BackgroundService::class.java).apply {
                action = ACTION_START
            }
        }

        fun createStopIntent(context: Context): Intent {
            return Intent(context, BackgroundService::class.java).apply {
                action = ACTION_STOP
            }
        }

        fun createPushIntent(
            context: Context,
            title: String?,
            body: String?
        ): Intent {
            return Intent(context, BackgroundService::class.java).apply {
                action = ACTION_PUSH
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_BODY, body)
            }
        }

        fun notifyAppForeground(context: Context) {
            Intent(context, BackgroundService::class.java).apply {
                action = ACTION_APP_FOREGROUND
                context.startService(this)
            }
        }

        fun notifyAppBackground(context: Context) {
            Intent(context, BackgroundService::class.java).apply {
                action = ACTION_APP_BACKGROUND
                context.startService(this)
            }
        }
    }
}