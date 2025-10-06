package com.exxlexxlee.atomicswap.core.database.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.exxlexxlee.atomicswap.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

	    single<AppDatabase> {
			Room.databaseBuilder(androidContext(), AppDatabase::class.java, "atomicswap.db")
				.fallbackToDestructiveMigration(true)
	            .build()
	    }

	    single { get<AppDatabase>().notificationsDao() }
	    single { get<AppDatabase>().swapDao() }

	    single<SharedPreferences> {
	        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
	    }
}
