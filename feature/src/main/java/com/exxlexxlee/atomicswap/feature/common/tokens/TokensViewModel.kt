package com.exxlexxlee.atomicswap.feature.common.tokens

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.TokensUseCase
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Action
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Action.OnDismissRequest
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Action.OnSelectToken
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Event
import com.exxlexxlee.atomicswap.feature.common.tokens.models.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TokensViewModel(
    private val tokensUseCase: TokensUseCase
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {
    companion object {
        private const val LIMIT = 20
    }

    private var page = 1
    private var filter = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            viewState = viewState.copy(fullCoins = tokensUseCase.topFullCoins( page * LIMIT))
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            Event.OnDismissRequest -> {
                viewAction = OnDismissRequest
            }

            is Event.OnSelectToken -> {
                viewAction = OnSelectToken(viewEvent.token)
            }

            is Event.Title -> {
                viewState = viewState.copy(title = viewEvent.title)
            }

            Event.OnLoadMore -> {
                viewModelScope.launch(Dispatchers.IO) {
                    page++
                    val current = if (filter.isBlank()) tokensUseCase.topFullCoins(page * LIMIT)
                    else tokensUseCase.fullCoins(filter, page * LIMIT)
                    viewState = viewState.copy(fullCoins = current)
                }
            }

            is Event.Filter -> viewModelScope.launch(Dispatchers.IO) {
                filter = viewEvent.text
                val current = if (filter.isBlank()) tokensUseCase.topFullCoins(page * LIMIT)
                else tokensUseCase.fullCoins(filter, page * LIMIT)
                viewState = viewState.copy(fullCoins = current)
            }

            Event.OnTokenView -> viewState = viewState.copy(isTokenView = !viewState.isTokenView)
            is Event.ChainCheck -> {
                val currentValue = viewState.isChainCheck[viewEvent.chain] ?: false
                viewState = viewState.copy(
                    isChainCheck = viewState.isChainCheck + (viewEvent.chain to !currentValue)
                )
            }
        }
    }

}