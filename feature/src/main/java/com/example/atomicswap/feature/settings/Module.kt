package com.example.atomicswap.feature.settings

import org.koin.dsl.module

val settingsModule = module {
	single { ThemeController() }
}
