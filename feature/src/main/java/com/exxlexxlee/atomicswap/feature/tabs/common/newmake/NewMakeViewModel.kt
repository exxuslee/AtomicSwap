package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.domain.usecases.MakeUseCase
import com.exxlexxlee.atomicswap.domain.usecases.PriceUseCase
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.ViewState
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


class NewMakeViewModel(
    makeId: String,
    private val makeUseCase: MakeUseCase,
    private val priceUseCase: PriceUseCase,
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
                viewModelScope.launch {
                    val make = viewState.make.copy(makerToken = viewEvent.token)
                    clearAction()
                    viewState = viewState.copy(
                        make = make, expandedMaker = false, price = price(
                            makeCoin = viewEvent.token?.coin,
                            takeCoin = viewState.make.takerToken?.coin,
                        )
                    )
                }

            }

            is Event.TakerToken -> {
                viewModelScope.launch {
                    val make = viewState.make.copy(takerToken = viewEvent.token)
                    clearAction()
                    viewState = viewState.copy(
                        make = make, expandedMaker = false, price = price(
                            makeCoin = viewState.make.makerToken?.coin,
                            takeCoin = viewEvent.token?.coin,
                        )
                    )
                }

            }

            Event.SwitchToken -> {
                viewModelScope.launch {
                    val makerToken = viewState.make.makerToken
                    val takerToken = viewState.make.takerToken
                    val make = viewState.make.copy(makerToken = takerToken, takerToken = makerToken)
                    viewState = viewState.copy(
                        make = make, price = price(
                            makeCoin = takerToken?.coin,
                            takeCoin = makerToken?.coin,
                        )
                    )
                }
            }

            is Event.SetDiscount -> {
                val make = viewState.make.copy(discount = goldenRatio(viewEvent.discountSlider))
                viewState = viewState.copy(make = make)
            }

            is Event.SetReserve -> {
                val adBalance = viewState.balance
                    ?.multiply(BigDecimal(viewEvent.reserveSlider.toDouble()))
                    ?.round(MathContext(4, RoundingMode.DOWN))
                val make = viewState.make.copy(adBalance = adBalance)
                viewState = viewState.copy(make = make)
            }
        }

    }

    private suspend fun price(
        makeCoin: Coin?,
        takeCoin: Coin?,
    ): BigDecimal? {
        if (makeCoin == null || takeCoin == null) return null
        val makePrice = priceUseCase.price(makeCoin)
        val takePrice = priceUseCase.price(takeCoin)
        if (makePrice == null || takePrice == null) return null
        val price = makePrice / takePrice
        val rounded = price.round(MathContext(4, RoundingMode.HALF_UP))
        return rounded
    }

    private fun goldenRatio(value: Float): Int = when {
        value > 5.5f -> 13
        value > 4.5f -> 8
        value > 3.5f -> 5
        value > 2.5f -> 3
        value > 1.5f -> 2
        value > 0.5f -> 1
        value > -0.5f -> 0
        value > -1.5f -> -1
        value > -2.5f -> -2
        value > -3.5f -> -3
        value > -4.5f -> -5
        value > -5.5f -> -8
        else -> -13
    }

}