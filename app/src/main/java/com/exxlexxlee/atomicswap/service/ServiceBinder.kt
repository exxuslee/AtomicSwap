package com.exxlexxlee.atomicswap.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import timber.log.Timber

/**
 * Helper object for binding to service and checking state
 */
object ServiceBinder {

    private var isBound = false
    private var service: BackgroundService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val localBinder = binder as? BackgroundService.LocalBinder
            service = localBinder?.getService()
            isBound = true
            Timber.d("Service connected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            isBound = false
            Timber.d("Service disconnected")
        }
    }

    fun bind(context: Context) {
        if (!isBound) {
            val intent = Intent(context, BackgroundService::class.java)
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun unbind(context: Context) {
        if (isBound) {
            context.unbindService(connection)
            isBound = false
            service = null
        }
    }

    fun isServiceRunning(): Boolean = isBound && service != null

    internal fun onServiceCreated() {
        Timber.d("Service instance created")
    }

    internal fun onServiceDestroyed() {
        service = null
        isBound = false
        Timber.d("Service instance destroyed")
    }
}