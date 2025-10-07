package com.exxlexxlee.atomicswap.feature.tabs.book

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.MakeUseCase
import com.exxlexxlee.atomicswap.feature.tabs.book.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.book.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.models.ViewState
import kotlinx.coroutines.launch

class BookViewModel(
    private val makeUseCase: MakeUseCase,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            makeUseCase.makesFlow.collect { swaps ->
                    viewState = viewState.copy(
                        makes = swaps,
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