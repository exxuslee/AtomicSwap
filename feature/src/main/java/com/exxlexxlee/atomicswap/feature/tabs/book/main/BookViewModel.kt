package com.exxlexxlee.atomicswap.feature.tabs.book.main

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.MakeUseCase
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.feature.tabs.book.main.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.book.main.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.main.models.ViewState
import kotlinx.coroutines.launch

class BookViewModel(
    private val settingsUseCase: SettingsUseCase,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        viewModelScope.launch {
            settingsUseCase.selectedFilterStateBook.collect {
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