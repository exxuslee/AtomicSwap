package com.example.atomicswap.core.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
	single {
		val context: Context = androidContext()
		AndroidSqliteDriver(AppDatabase.Schema, context, "atomicswap.db")
	}
}
