package com.example.atomicswap.domain.repository

interface SettingsRepository {
	fun isDark(): Boolean
	fun isDark(value: Boolean)

	fun languageTag(): String
	fun languageTag(tag: String)
}
