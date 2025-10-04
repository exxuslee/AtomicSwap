package com.exxlexxlee.atomicswap.push

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.exxlexxlee.atomicswap.R
import com.exxlexxlee.atomicswap.service.BackgroundManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

/**
 * Firebase Cloud Messaging service for handling push notifications
 */
class PushMessagingService : FirebaseMessagingService() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannelIfNeeded()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("FCM token refreshed: $token")
        // TODO: Send token to your backend server
        handleTokenRefresh(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.d("Push notification received from: ${message.from}")

        val notificationData = extractNotificationData(message)

        // Forward to BackgroundService for processing
        BackgroundManager.handlePushNotification(
            context = this,
            title = notificationData.title,
            body = notificationData.body
        )

        // Show notification to user
        if (hasNotificationPermission()) {
            showNotification(notificationData)
        } else {
            Timber.w("Notification permission not granted")
        }
    }

    private fun extractNotificationData(message: RemoteMessage): NotificationData {
        val title = message.notification?.title
            ?: message.data["title"]
            ?: getString(R.string.app_name)

        val body = message.notification?.body
            ?: message.data["body"]
            ?: ""

        return NotificationData(title, body, message.data)
    }

    private fun showNotification(data: NotificationData) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(data.title)
            .setContentText(data.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(this)
                .notify(generateNotificationId(), notification)
        } catch (e: SecurityException) {
            Timber.e(e, "Failed to show notification - permission denied")
        }
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // No runtime permission needed below Android 13
        }
    }

    private fun createNotificationChannelIfNeeded() {
        val channel = android.app.NotificationChannel(
            CHANNEL_ID,
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.notification_channel_description)
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun handleTokenRefresh(token: String) {
        // Implement your token registration logic here
        // Example: send to your backend server
        Timber.d("TODO: Send FCM token to backend: $token")
    }

    private fun generateNotificationId(): Int {
        return System.currentTimeMillis().toInt()
    }

    private data class NotificationData(
        val title: String,
        val body: String,
        val customData: Map<String, String> = emptyMap()
    )

    companion object {
        const val CHANNEL_ID = "atomic_swap_default"
    }
}