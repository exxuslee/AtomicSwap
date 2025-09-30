package com.exxlexxlee.atomicswap.core.common.base

import android.app.Application
import com.exxlexxlee.atomicswap.core.common.BuildConfig
import timber.log.Timber

abstract class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}

