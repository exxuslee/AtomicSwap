package com.exxlexxlee.atomicswap.service

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

class AppLifecycleObserver(
    private val context: android.content.Context
) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        BackgroundService.notifyAppForeground(context)
        Timber.d("App moved to foreground")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        BackgroundService.notifyAppBackground(context)
        Timber.d("App moved to background")
    }
}