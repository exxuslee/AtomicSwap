package com.exxlexxlee.atomicswap.feature.settings.aggregator

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.AggregatorUseCase
import com.exxlexxlee.atomicswap.feature.settings.aggregator.models.Action
import com.exxlexxlee.atomicswap.feature.settings.aggregator.models.Event
import com.exxlexxlee.atomicswap.feature.settings.aggregator.models.ViewState
import kotlinx.coroutines.launch

class AggregatorViewModel(
    private val aggregatorUseCase: AggregatorUseCase,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        viewState = viewState.copy(
            emitters = aggregatorUseCase.aggregators,
        )

        viewModelScope.launch {
            aggregatorUseCase.selected.collect { selected ->
                viewState = viewState.copy(selected = selected)
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.Select -> {
                aggregatorUseCase.select(viewEvent.label)
            }
        }

    }

}