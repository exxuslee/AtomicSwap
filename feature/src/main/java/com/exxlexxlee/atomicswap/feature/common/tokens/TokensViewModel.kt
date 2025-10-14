package com.exxlexxlee.atomicswap.feature.common.tokens

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.TokensUseCase
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Action
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Event
import com.exxlexxlee.atomicswap.feature.common.tokens.models.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TokensViewModel(
    private val tokensUseCase: TokensUseCase
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            viewState = viewState.copy(fullCoins = tokensUseCase.fullCoins(""))
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }
    }

}