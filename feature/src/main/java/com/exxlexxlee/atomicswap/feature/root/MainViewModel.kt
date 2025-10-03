package com.exxlexxlee.atomicswap.feature.root

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.domain.usecases.SwapUseCase
import com.exxlexxlee.atomicswap.feature.navigation.Routes
import com.exxlexxlee.atomicswap.feature.root.models.Action
import com.exxlexxlee.atomicswap.feature.root.models.Event
import com.exxlexxlee.atomicswap.feature.root.models.ViewState
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingsUseCase: SettingsUseCase,
    private val swapUseCase: SwapUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        initialRoute = settingsUseCase.selectedRoute(),
        makerRoute = Routes.MakerRoute(),
        chronicleRoute = Routes.ChronicleRoute.MainRoute(swapUseCase.mainBadgeType()),
        settingsRoute = Routes.SettingsRoute.MainRoute(settingsUseCase.badgeType()),
        selectedChronicleTab = settingsUseCase.selectedFilterStateChronicle()
    )
) {

    init {
        viewModelScope.launch {
            settingsUseCase.isTermsOfUseRead.collect {
                viewState = viewState.copy(
                    settingsRoute = Routes.SettingsRoute.MainRoute(settingsUseCase.badgeType()),
                )
            }
        }
        viewModelScope.launch {
            settingsUseCase.selectedFilterStateChronicle.collect {
                viewState = viewState.copy(selectedChronicleTab = it)
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.SelectedRoute -> settingsUseCase.selectedRoute(viewEvent.route)
        }

    }

}