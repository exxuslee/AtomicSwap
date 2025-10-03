package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.model.SwapState
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.ViewState
import kotlinx.coroutines.launch

class ChronicleViewModel(
    private val swapUseCase: SwapUseCase,
    private val settingsUseCase: SettingsUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        selectedTab = settingsUseCase.selectedFilterStateChronicle()
    )
) {

    init {
        loadSwaps()
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
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

    private fun selectTab(filterState: FilterStateChronicle) {
        viewState = viewState.copy(
            selectedTab = filterState,
            filteredSwaps = filterSwaps(viewState.allSwaps, filterState)
        )
        settingsUseCase.selectedFilterStateChronicle(filterState)
    }

    private fun filterSwaps(swaps: List<Swap>, filterState: FilterStateChronicle): List<Swap> {
        return when (filterState) {
            FilterStateChronicle.MAKE -> swaps
            FilterStateChronicle.ACTIVE -> swaps.filter { swap ->
                swap.swapState in listOf(
                    SwapState.REQUESTED,
                    SwapState.RESPONDED,
                    SwapState.INITIATOR_BAILED,
                    SwapState.RESPONDER_BAILED
                )
            }
            FilterStateChronicle.COMPLETE -> swaps.filter { swap ->
                swap.swapState in listOf(
                    SwapState.INITIATOR_REDEEMED,
                    SwapState.RESPONDER_REDEEMED
                )
            }
            FilterStateChronicle.REFUND -> swaps.filter { swap ->
                swap.swapState == SwapState.REFUNDED
            }
        }
    }
}