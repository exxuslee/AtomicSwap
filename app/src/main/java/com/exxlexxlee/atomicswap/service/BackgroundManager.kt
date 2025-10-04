package com.exxlexxlee.atomicswap.service

import android.content.Context
import timber.log.Timber

object BackgroundManager {

    fun startService(context: Context): Result<Unit> {
        return runCatching {
            if (isServiceRunning()) {
                Timber.d("BackgroundService is already running")
                return@runCatching
            }

            val intent = BackgroundService.createStartIntent(context)
            context.startForegroundService(intent)
            ServiceBinder.bind(context)
            Timber.d("BackgroundService started")
        }.onFailure { error ->
            Timber.e(error, "Failed to start BackgroundService")
        }
    }

    fun stopService(context: Context): Result<Unit> {
        return runCatching {
            if (!isServiceRunning()) {
                Timber.d("BackgroundService is not running")
                return@runCatching
            }

            ServiceBinder.unbind(context)
            val intent = BackgroundService.createStopIntent(context)
            context.stopService(intent)
            Timber.d("BackgroundService stopped")
        }.onFailure { error ->
            Timber.e(error, "Failed to stop BackgroundService")
        }
    }

    fun handlePushNotification(
        context: Context,
        title: String?,
        body: String?
    ): Result<Unit> {
        return runCatching {
            val intent = BackgroundService.createPushIntent(context, title, body)
            context.startService(intent)
            Timber.d("Push notification forwarded to BackgroundService")
        }.onFailure { error ->
            Timber.e(error, "Failed to handle push notification")
        }
    }

    fun restartService(context: Context): Result<Unit> {
        return runCatching {
            stopService(context).getOrThrow()
            // Small delay to ensure service is fully stopped
            Thread.sleep(500)
            startService(context).getOrThrow()
            Timber.d("BackgroundService restarted")
        }.onFailure { error ->
            Timber.e(error, "Failed to restart BackgroundService")
        }
    }

    fun isServiceRunning() = ServiceBinder.isServiceRunning()

    fun bindIfRunning(context: Context) {
        if (!ServiceBinder.isServiceRunning()) {
            ServiceBinder.bind(context)
            Timber.d("Bound to existing BackgroundService")
        }
    }

    fun unbind(context: Context) {
        ServiceBinder.unbind(context)
        Timber.d("Unbound from BackgroundService")
    }
}