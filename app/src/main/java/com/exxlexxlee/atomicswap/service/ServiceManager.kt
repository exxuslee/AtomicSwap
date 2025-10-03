package com.exxlexxlee.atomicswap.service

import android.content.Context
import android.content.Intent
import android.os.Build
import timber.log.Timber

object ServiceManager {
    
    private var isServiceRunning = false
    
    /**
     * Запускает BackgroundService
     */
    fun startBackgroundService(context: Context) {
        try {
            if (!isServiceRunning) {
                BackgroundService.startService(context)
                isServiceRunning = true
                Timber.d("BackgroundService started via ServiceManager")
            } else {
                Timber.d("BackgroundService is already running")
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to start BackgroundService")
        }
    }
    
    /**
     * Останавливает BackgroundService
     */
    fun stopBackgroundService(context: Context) {
        try {
            if (isServiceRunning) {
                BackgroundService.stopService(context)
                isServiceRunning = false
                Timber.d("BackgroundService stopped via ServiceManager")
            } else {
                Timber.d("BackgroundService is not running")
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to stop BackgroundService")
        }
    }
    
    /**
     * Проверяет, запущен ли сервис
     */
    fun isServiceRunning(): Boolean = isServiceRunning
    
    /**
     * Обрабатывает push-уведомление через BackgroundService
     */
    fun handlePushNotification(context: Context, title: String? = null, body: String? = null) {
        try {
            BackgroundService.onPushReceived(context, title, body)
            Timber.d("Push notification handled via ServiceManager")
        } catch (e: Exception) {
            Timber.e(e, "Failed to handle push notification")
        }
    }
    
    /**
     * Перезапускает сервис
     */
    fun restartBackgroundService(context: Context) {
        stopBackgroundService(context)
        startBackgroundService(context)
        Timber.d("BackgroundService restarted via ServiceManager")
    }
}
