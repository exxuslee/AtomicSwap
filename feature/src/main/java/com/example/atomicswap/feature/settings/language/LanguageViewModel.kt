package com.example.atomicswap.feature.settings.language

import android.content.Context
import com.example.atomicswap.core.ui.base.BaseViewModel
import com.example.atomicswap.feature.settings.language.models.Action
import com.example.atomicswap.feature.settings.language.models.Event
import com.example.atomicswap.feature.settings.language.models.LanguageViewItem
import com.example.atomicswap.feature.settings.language.models.ViewState
import com.hwasfy.localize.api.LanguageManager
import com.hwasfy.localize.util.SupportedLocales

class LanguageViewModel(
    context: Context,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    private val appContext: Context = context

    init {
        viewState = buildViewState()
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.Select -> {
                viewAction = Action.SetLocale(viewEvent.type)
                viewState = viewState.copy(
                    languageItems = viewState.languageItems.map {
                        it.copy(current = it.localeType == viewEvent.type)
                    }
                )
            }

        }

    }

    fun refresh() {
        viewState = buildViewState()
    }

    private fun buildViewState(): ViewState {
        val current = LanguageManager.getCurrentLocale(appContext)
        return ViewState(
            languageItems = SupportedLocales.entries.map {
                LanguageViewItem(
                    localeType = it,
                    name = it.locale.displayLanguage,
                    nativeName = it.locale.displayName,
                    icon = it.icon,
                    current = current == it
                )
            }
        )
    }

}