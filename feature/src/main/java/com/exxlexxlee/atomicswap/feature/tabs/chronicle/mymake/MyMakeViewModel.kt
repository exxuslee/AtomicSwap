package com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake.models.ViewState

class MyMakeViewModel(
    private val swapUseCase: SwapUseCase,
): BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}


