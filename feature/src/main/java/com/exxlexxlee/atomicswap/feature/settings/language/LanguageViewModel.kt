package com.exxlexxlee.atomicswap.feature.settings.language

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.settings.language.models.Action
import com.exxlexxlee.atomicswap.feature.settings.language.models.Event
import com.exxlexxlee.atomicswap.feature.settings.language.models.ViewState

class LanguageViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.Select -> viewAction = Action.SetLocale(viewEvent.type)
            is Event.PopBackStack -> viewAction = Action.PopBackStack
        }

    }

}