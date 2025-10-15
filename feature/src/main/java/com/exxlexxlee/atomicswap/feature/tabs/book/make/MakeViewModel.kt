package com.exxlexxlee.atomicswap.feature.tabs.book.make

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.tabs.book.make.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.book.make.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.make.models.ViewState

class MakeViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {


    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            Event.MakerTokenSheet -> {
                viewState = viewState.copy(expandedMaker = !viewState.expandedMaker)
                viewAction = Action.MakerToken
            }

            Event.TakerTokenSheet -> {
                viewState = viewState.copy(expandedTaker = !viewState.expandedTaker)
                viewAction = Action.TakerToken
            }

            Event.ClearAction -> {
                viewState = viewState.copy(expandedTaker = false, expandedMaker = false)
                clearAction()
            }

            is Event.MakerToken -> viewState = viewState.copy(
                filterToken = viewState.filterToken.first to viewEvent.token,
                expandedMaker = false,
            ).also { clearAction() }

            is Event.TakerToken -> viewState = viewState.copy(
                filterToken = viewEvent.token to viewState.filterToken.second,
                expandedTaker = false,

                ).also { clearAction() }

            Event.SwitchToken -> viewState = viewState.copy(
                filterToken = viewState.filterToken.second to viewState.filterToken.first
            )
        }
    }

}