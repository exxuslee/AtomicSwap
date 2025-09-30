package com.exxlexxlee.atomicswap.feature.settings.terms

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.feature.settings.terms.models.Action
import com.exxlexxlee.atomicswap.feature.settings.terms.models.Event
import com.exxlexxlee.atomicswap.feature.settings.terms.models.ViewState

class TermsViewModel(
    private val settingsUseCase: SettingsUseCase
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        isTermsOfUseRead = settingsUseCase.isTermsOfUseRead()
    )
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.PopBackStack -> viewAction = Action.PopBackStack
            is Event.ReadTerms -> {
                settingsUseCase.isTermsOfUseRead(true)
                viewState = viewState.copy(isTermsOfUseRead = true)
            }
        }

    }

}