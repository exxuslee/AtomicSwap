package com.exxlexxlee.atomicswap.feature.tabs.book.my

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.MakeUseCase
import com.exxlexxlee.atomicswap.feature.tabs.book.my.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.book.my.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.my.models.ViewState
import kotlinx.coroutines.launch

class MyMakeViewModel(
    private val makeUseCase: MakeUseCase,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        viewModelScope.launch {
            makeUseCase.makesFlow.collect {
                viewState = viewState.copy(isLoading = false, makes = it)
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}