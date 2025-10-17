package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.MakeUseCase
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.ViewState
import timber.log.Timber
import java.math.BigDecimal


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

            is Event.MakerToken -> {
                val make = viewState.make.copy(makerToken = viewEvent.token)
                clearAction()
                viewState = viewState.copy(make = make, expandedMaker = false)
            }

            is Event.TakerToken -> {
                val make = viewState.make.copy(takerToken = viewEvent.token)
                clearAction()
                viewState = viewState.copy(make = make, expandedMaker = false)
            }

            Event.SwitchToken -> {
                val makerToken = viewState.make.makerToken
                val takerToken = viewState.make.takerToken
                val make = viewState.make.copy(makerToken = takerToken, takerToken = makerToken)
                viewState = viewState.copy(make = make)
            }

            is Event.SetDiscount -> {
                val make = viewState.make.copy(discount = goldenRatio(viewEvent.discountSlider))
                viewState = viewState.copy(
                    discountSlider = viewEvent.discountSlider, make = make
                )
            }
        }

    }

    private fun goldenRatio(value: Float): Int = when {
        value > 6f -> 13
        value > 5f -> 8
        value > 4f -> 5
        value > 3f -> 3
        value > 2f -> 2
        value > 1f -> 1
        value > 0f -> 0
        value > -1f -> -1
        value > -2f -> -2
        value > -3f -> -3
        value > -4f -> -5
        value > -5f -> -8
        else -> -13
    }

}