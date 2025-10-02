package com.exxlexxlee.atomicswap.feature.chronicle.main

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.Event
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.Action
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.ViewState


class ChronicleViewModel(
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