package com.exxlexxlee.atomicswap.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.exxlexxlee.atomicswap.R

object NotificationChannels {

    const val CHANNEL_ID = "atomic_swap_default"
    const val NOTIFICATION_ID = 1001
    const val SERVICE_CHANNEL_ID: String = "background_service_channel"

    fun registerAll(context: Context) {
        val manager = context.getSystemService(NotificationManager::class.java)
        val channels = listOf(
            createPushChannel(context),
            createServiceChannel(context)
        )
        channels.forEach { manager.createNotificationChannel(it) }
    }

    fun createPushChannel(context: Context): NotificationChannel {
        return NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_channel_description)
            enableVibration(true)
            setShowBadge(true)
        }
    }

    fun createServiceChannel(context: Context): NotificationChannel {
        return NotificationChannel(
            SERVICE_CHANNEL_ID,
            context.getString(R.string.service_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = context.getString(R.string.service_channel_description)
            setShowBadge(false)
        }
    }
}