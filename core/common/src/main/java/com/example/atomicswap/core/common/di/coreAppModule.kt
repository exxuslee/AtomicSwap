package com.example.atomicswap.core.common.di

import android.app.Application
import com.example.atomicswap.core.common.base.CoreApp
import org.koin.dsl.module

val coreAppModule = module {
    single<Application> { get<CoreApp>() }
}