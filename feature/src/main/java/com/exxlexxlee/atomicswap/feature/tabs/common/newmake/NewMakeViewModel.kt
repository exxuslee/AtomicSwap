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
                tokenPair = viewState.tokenPair.first to viewEvent.token,
                expandedMaker = false,
            ).also { clearAction() }

            is Event.TakerToken -> viewState = viewState.copy(
                tokenPair = viewEvent.token to viewState.tokenPair.second,
                expandedTaker = false,

                ).also { clearAction() }

            Event.SwitchToken -> viewState = viewState.copy(
                tokenPair = viewState.tokenPair.second to viewState.tokenPair.first
            )
        }

    }

}