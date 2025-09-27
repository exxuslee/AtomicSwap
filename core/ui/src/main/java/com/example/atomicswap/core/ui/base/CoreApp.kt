package com.example.atomicswap.core.ui.base

import android.app.Application

abstract class CoreApp : Application() {

    companion object : ICoreApp {
        override lateinit var instance: CoreApp
    }
}
