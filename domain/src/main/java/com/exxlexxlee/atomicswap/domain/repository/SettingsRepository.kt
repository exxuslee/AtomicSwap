package com.exxlexxlee.atomicswap.domain.repository

interface SettingsRepository {
	fun isDark(): Boolean
	fun isDark(value: Boolean)
	fun selectedRoute(): String
	fun selectedRoute(route: String)
}
