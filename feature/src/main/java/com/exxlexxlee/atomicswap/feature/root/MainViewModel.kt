package com.exxlexxlee.atomicswap.feature.root

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.feature.navigation.RoutesMain
import com.exxlexxlee.atomicswap.feature.root.models.Action
import com.exxlexxlee.atomicswap.feature.root.models.Event
import com.exxlexxlee.atomicswap.feature.root.models.ViewState
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingsUseCase: SettingsUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        initialRoute = settingsUseCase.selectedRoute(),
        taker = RoutesMain.Taker(),
        maker = RoutesMain.Maker(),
        history = RoutesMain.History(),
        settings = RoutesMain.Settings.Main(settingsUseCase.badgeType()),
    )
) {

    init {
        viewModelScope.launch {
            settingsUseCase.isTermsOfUseRead.collect {
                viewState = viewState.copy(
                    settings = RoutesMain.Settings.Main(settingsUseCase.badgeType()),
                )
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.SelectedRoute -> settingsUseCase.selectedRoute(viewEvent.route)
        }

    }

}