package com.exxlexxlee.atomicswap.domain.repository

import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators

interface SettingsRepository {
	fun isDark(): Boolean
	fun isDark(value: Boolean)
	fun selectedRoute(): String
	fun selectedRoute(route: String)

	fun isTermsOfUseRead(): Boolean
	fun isTermsOfUseRead(ok: Boolean)
	fun selectedAggregator(): String
	fun selectedAggregator(label: String)
}
