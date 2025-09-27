package com.example.atomicswap.core.ui.di

import android.app.Application
import com.example.atomicswap.core.ui.base.CoreApp
import org.koin.dsl.module

val coreAppModule = module {
    single<Application> { get<CoreApp>() }
}