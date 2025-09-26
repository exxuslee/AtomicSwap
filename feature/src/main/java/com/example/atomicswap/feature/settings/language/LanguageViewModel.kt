package com.example.atomicswap.feature.settings.language

import com.example.atomicswap.core.ui.base.BaseViewModel
import com.example.atomicswap.feature.settings.language.models.Action
import com.example.atomicswap.feature.settings.language.models.Event
import com.example.atomicswap.feature.settings.language.models.ViewState

class LanguageViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.Select -> {
                viewAction = Action.SetLocale(viewEvent.type)
            }

        }

    }

}