package com.exxlexxlee.atomicswap.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.exxlexxlee.atomicswap.MainActivity
import com.exxlexxlee.atomicswap.R
import timber.log.Timber

class BackgroundService : Service() {

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "background_service_channel"
        private const val CHANNEL_NAME = "Background Service"
        
        const val ACTION_START_SERVICE = "com.exxlexxlee.atomicswap.START_SERVICE"
        const val ACTION_STOP_SERVICE = "com.exxlexxlee.atomicswap.STOP_SERVICE"
        const val ACTION_PUSH_RECEIVED = "com.exxlexxlee.atomicswap.PUSH_RECEIVED"
        
        @RequiresApi(Build.VERSION_CODES.O)
        fun startService(context: Context) {
            val intent = Intent(context, BackgroundService::class.java).apply {
                action = ACTION_START_SERVICE
            }
            context.startForegroundService(intent)
        }
        
        fun stopService(context: Context) {
            val intent = Intent(context, BackgroundService::class.java).apply {
                action = ACTION_STOP_SERVICE
            }
            context.stopService(intent)
        }
        
        fun onPushReceived(context: Context, title: String? = null, body: String? = null) {
            val intent = Intent(context, BackgroundService::class.java).apply {
                action = ACTION_PUSH_RECEIVED
                putExtra("title", title)
                putExtra("body", body)
            }
            context.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Timber.d("BackgroundService created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_SERVICE -> {
                startForegroundService()
                Timber.d("BackgroundService started")
            }
            ACTION_STOP_SERVICE -> {
                stopSelf()
                Timber.d("BackgroundService stopped")
            }
            ACTION_PUSH_RECEIVED -> {
                handlePushReceived(intent)
                Timber.d("BackgroundService received push notification")
            }
        }
        
        // Возвращаем START_STICKY чтобы сервис перезапускался при завершении
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("BackgroundService destroyed")
    }

    private fun startForegroundService() {
        val notification = createNotification("Сервис запущен", "Фоновый сервис работает")
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun handlePushReceived(intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Push уведомление"
        val body = intent.getStringExtra("body") ?: "Получено новое уведомление"
        
        // Обновляем уведомление с информацией о push
        val notification = createNotification(title, body)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
        
        // Здесь можно добавить дополнительную логику обработки push-уведомления
        processPushNotification(title, body)
    }

    private fun processPushNotification(title: String, body: String) {
        // Здесь можно добавить логику обработки push-уведомления
        // Например, обновление данных, синхронизация, отправка аналитики и т.д.
        Timber.d("Processing push notification: $title - $body")
        
        // Пример: можно запустить дополнительную работу в фоне
        Thread {
            // Имитация обработки
            Thread.sleep(2000)
            Timber.d("Push notification processed successfully")
        }.start()
    }

    private fun createNotification(title: String, content: String): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Канал для фонового сервиса"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
