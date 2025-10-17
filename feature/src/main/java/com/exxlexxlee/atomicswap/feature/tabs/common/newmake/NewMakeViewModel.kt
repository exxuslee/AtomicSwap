package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.core.swap.model.PriceType.*
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

            Event.SetFixedPrice -> {
                val make = viewState.make.copy(priceType = Fixed(BigDecimal.ONE))
                viewState = viewState.copy(make = make)
            }

            Event.SetMarketPrice -> {
                val make = viewState.make.copy(priceType = Market(2))
                viewState = viewState.copy(make = make)
            }

            is Event.SetDiscount -> {
                Timber.d("${viewEvent.discountSlider}")
            }
        }

    }

}