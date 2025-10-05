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
        val id = intent?.action ?: return START_STICKY
        val action = ActionBackground.fromId(id)
        Timber.d("onStartCommand: $action ")
        when (action) {
            ActionBackground.Start -> handleStartService()
            ActionBackground.Stop -> handleStopService()
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
        startPeriodicTask()
        Timber.d("BackgroundService started in foreground (silent: $isAppInForeground)")
    }

    private fun handleStopService() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        Timber.d("BackgroundService stopped")
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

    private fun buildNotification(
        title: String,
        content: String,
        silent: Boolean = false
    ): Notification {
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

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, BackgroundService::class.java).apply {
                action = ActionBackground.Start.id
            }
        }

        fun createStopIntent(context: Context): Intent {
            return Intent(context, BackgroundService::class.java).apply {
                action = ActionBackground.Stop.id
            }
        }
    }
}