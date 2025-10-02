package com.exxlexxlee.atomicswap.feature.chronicle.main

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.model.SwapState
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.Action
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.Event
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.SwapFilterState
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.ViewState
import kotlinx.coroutines.launch

class ChronicleViewModel(
    private val swapUseCase: SwapUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    init {
        loadSwaps()
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.OpenSwap -> {
                viewAction = Action.OpenSwap(viewEvent.swapId)
            }
            is Event.SelectTab -> {
                selectTab(viewEvent.filterState)
            }
        }
    }

    private fun loadSwaps() {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            swapUseCase.swapsFlow.collect { swaps ->
                viewState = viewState.copy(
                    allSwaps = swaps,
                    filteredSwaps = filterSwaps(swaps, viewState.selectedTab),
                    isLoading = false
                )
            }
        }
    }

    private fun selectTab(filterState: SwapFilterState) {
        viewState = viewState.copy(
            selectedTab = filterState,
            filteredSwaps = filterSwaps(viewState.allSwaps, filterState)
        )
    }

    private fun filterSwaps(swaps: List<com.exxlexxlee.atomicswap.domain.model.Swap>, filterState: SwapFilterState): List<com.exxlexxlee.atomicswap.domain.model.Swap> {
        return when (filterState) {
            SwapFilterState.ALL -> swaps
            SwapFilterState.ACTIVE -> swaps.filter { swap ->
                swap.swapState in listOf(
                    SwapState.REQUESTED,
                    SwapState.RESPONDED,
                    SwapState.INITIATOR_BAILED,
                    SwapState.RESPONDER_BAILED
                )
            }
            SwapFilterState.REDEEM -> swaps.filter { swap ->
                swap.swapState in listOf(
                    SwapState.INITIATOR_REDEEMED,
                    SwapState.RESPONDER_REDEEMED
                )
            }
            SwapFilterState.REFUND -> swaps.filter { swap ->
                swap.swapState == SwapState.REFUNDED
            }
        }
    }
}