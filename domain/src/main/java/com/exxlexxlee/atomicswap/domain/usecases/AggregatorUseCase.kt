package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

interface AggregatorUseCase {
    val aggregators: List<SupportedAggregators>
    val selected: Flow<SupportedAggregators>

    fun select(label: String)

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : AggregatorUseCase {

        override val aggregators = SupportedAggregators.entries

        override val selected = settingsRepository.selectedAggregator

        override fun select(label: String) {
            settingsRepository.selectedAggregator(label)
        }

    }
}