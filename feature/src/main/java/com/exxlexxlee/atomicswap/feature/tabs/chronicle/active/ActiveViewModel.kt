package com.exxlexxlee.atomicswap.feature.tabs.chronicle.active

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.models.ViewState

class ActiveViewModel(
    private val swapUseCase: SwapUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}