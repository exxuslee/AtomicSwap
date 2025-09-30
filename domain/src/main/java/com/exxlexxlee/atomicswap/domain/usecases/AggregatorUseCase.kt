package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AggregatorUseCase {
    val aggregators: List<SupportedAggregators>
    val selected: Flow<SupportedAggregators>

    fun select(label: String)

    class Base(
        private val settingsRepository: SettingsRepository,
    ) : AggregatorUseCase {

        override val aggregators = SupportedAggregators.entries

        private val _selected = MutableStateFlow(
            SupportedAggregators.fromLabel(settingsRepository.selectedAggregator())
        )
        override val selected: StateFlow<SupportedAggregators> = _selected

        override fun select(label: String) {
            settingsRepository.selectedAggregator(label)
            _selected.value = SupportedAggregators.fromLabel(label)
        }

    }
}