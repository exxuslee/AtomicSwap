package com.exxlexxlee.atomicswap.feature.tabs.chronicle.complete

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.complete.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.complete.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.complete.models.ViewState
import kotlinx.coroutines.launch

class CompleteViewModel(
    private val swapUseCase: SwapUseCase,
): BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            swapUseCase.swaps(FilterStateChronicle.Complete)
                .collect { swaps ->
                    viewState = viewState.copy(
                        swaps = swaps,
                        isLoading = false
                    )
                }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}