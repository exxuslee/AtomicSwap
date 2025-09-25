package com.example.atomicswap

import android.app.Application
import com.example.atomicswap.core.database.databaseModule
import com.example.atomicswap.core.network.networkModule
import com.example.atomicswap.data.di.dataModule
import com.example.atomicswap.feature.history.historyModule
import com.example.atomicswap.feature.maker.makerModule
import com.example.atomicswap.feature.settings.di.settingsModule
import com.example.atomicswap.feature.taker.takerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AtomicSwapApp : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidLogger()
			androidContext(this@AtomicSwapApp)
			modules(
				networkModule,
				databaseModule,
				dataModule,
				takerModule,
				makerModule,
				historyModule,
				settingsModule
			)
		}
	}
}
