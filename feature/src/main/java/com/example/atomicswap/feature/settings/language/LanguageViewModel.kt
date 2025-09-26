package com.example.atomicswap.feature.settings.language

import android.content.Context
import com.example.atomicswap.core.ui.base.BaseViewModel
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.language.models.Action
import com.example.atomicswap.feature.settings.language.models.Event
import com.example.atomicswap.feature.settings.language.models.LanguageViewItem
import com.example.atomicswap.feature.settings.language.models.ViewState
import com.hwasfy.localize.api.LanguageManager
import com.hwasfy.localize.util.SupportedLocales

class LanguageViewModel(
    context: Context,
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    init {
        val currentTag = LanguageManager.getCurrentLocale(context).tag
        viewState = ViewState(
            languageItems = listOf(
                LanguageViewItem(
                    localeType = SupportedLocales.EN_US,
                    name = "English",
                    nativeName = "English",
                    icon = R.drawable.outline_language_24,
                    current = currentTag == SupportedLocales.EN_US.tag
                ),
                LanguageViewItem(
                    localeType = SupportedLocales.RU_RU,
                    name = "Russian",
                    nativeName = "Русский",
                    icon = R.drawable.outline_language_24,
                    current = currentTag == SupportedLocales.RU_RU.tag
                ),
            )
        )
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
            else -> {}
        }

    }

}