package com.example.atomicswap.domain.repository

interface SettingsRepository {
	fun isDark(): Boolean
	fun setDark(value: Boolean)
}
