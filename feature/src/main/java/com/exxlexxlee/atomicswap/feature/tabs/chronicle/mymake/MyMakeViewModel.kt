package com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake.models.ViewState
import kotlinx.coroutines.launch

class MyMakeViewModel(
    private val swapUseCase: SwapUseCase,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        viewModelScope.launch {
            viewState = viewState.copy(isLoading = true)
            swapUseCase.swaps(FilterStateChronicle.MyMake).collect { swaps ->
                    viewState = viewState.copy(
                        swaps = swaps, isLoading = false
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


