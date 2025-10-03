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
        viewState = viewState.copy(badeType = swapUseCase.filterBadgeType())
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.SelectTab -> {
                selectTab(viewEvent.filterState)
            }
        }
    }

    private fun selectTab(filterState: FilterStateChronicle) {
        viewState = viewState.copy(
            selectedTab = filterState,
        )
        settingsUseCase.selectedFilterStateChronicle(filterState)
    }

}