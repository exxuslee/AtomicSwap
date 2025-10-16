package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.MakeUseCase
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.ViewState


class NewMakeViewModel(
    makeId: String,
    private val makeUseCase: MakeUseCase,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState(makeId)) {


    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            else -> {}
        }

    }

}