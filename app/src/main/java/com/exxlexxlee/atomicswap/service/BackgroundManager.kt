package com.exxlexxlee.atomicswap.service

import android.content.Context
import timber.log.Timber

object BackgroundManager {

    fun startForegroundService(context: Context): Result<Unit> {
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

    fun startService(context: Context): Result<Unit> {
        return runCatching {
            if (isServiceRunning()) {
                Timber.d("BackgroundService is already running")
                return@runCatching
            }
            val intent = BackgroundService.createStartIntent(context)
            context.startService(intent)
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