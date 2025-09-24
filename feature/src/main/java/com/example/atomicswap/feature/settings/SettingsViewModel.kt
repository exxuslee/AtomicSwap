package com.example.atomicswap.feature.settings

import com.example.atomicswap.core.ui.base.BaseViewModel
import com.example.atomicswap.feature.settings.models.SettingsAction
import com.example.atomicswap.feature.settings.models.SettingsEvent
import com.example.atomicswap.feature.settings.models.SettingsViewState


class SettingsViewModel(
    private val themeController: ThemeController,
) : BaseViewModel<SettingsViewState, SettingsAction,
        SettingsEvent>(initialState = SettingsViewState()) {

    override fun obtainEvent(viewEvent: SettingsEvent) {
        when (viewEvent) {
            is SettingsEvent.IsDark -> {
                themeController.setDark(viewEvent.newValue)
                viewState = viewState.copy(isDark = viewEvent.newValue)
            }

            SettingsEvent.MainAction -> viewAction = SettingsAction.OpenMainScreen
        }

    }
}