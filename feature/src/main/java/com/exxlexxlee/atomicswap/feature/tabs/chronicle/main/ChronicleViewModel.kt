package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.ViewState
import kotlinx.coroutines.launch

class ChronicleViewModel(
    private val settingsUseCase: SettingsUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        selectedTab = settingsUseCase.selectedFilterStateChronicle()
    )
) {

    init {
        viewModelScope.launch {
            settingsUseCase.selectedFilterStateChronicle.collect {
                viewState = viewState.copy(selectedTab = it)
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }



}