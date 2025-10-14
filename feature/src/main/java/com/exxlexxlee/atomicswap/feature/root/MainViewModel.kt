package com.exxlexxlee.atomicswap.feature.root

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.domain.usecases.PushReaderUseCase
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
    private val pushReaderUseCase: PushReaderUseCase,
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
            swapUseCase.swapFilterBadgeType.collect {
                viewState = viewState.copy(swapFilterBadgeType = it)
            }
        }
        viewModelScope.launch {
            pushReaderUseCase.unreadCount.collect {
                viewState = viewState.copy(pushUnreadCount = it)
            }
        }
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
        viewModelScope.launch {
            swapUseCase.mainBadgeType.collect {
                viewState = viewState.copy(
                    chronicleRoute = Routes.ChronicleRoute.MainRoute(it)
                )
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.MainRoute -> settingsUseCase.selectedRoute(viewEvent.route)
            is Event.ChronicleTab -> {
                settingsUseCase.selectedFilterStateChronicle(viewEvent.filterState)
            }

            Event.MakerTokenSheet -> viewAction = Action.MakerToken
            Event.TakerTokenSheet -> viewAction = Action.TakerToken
            Event.ClearAction -> clearAction()
            is Event.MakerToken -> viewState = viewState.copy(
                filterToken = viewState.filterToken.first to viewEvent.token
            ).also { clearAction() }

            is Event.TakerToken -> viewState = viewState.copy(
                filterToken = viewEvent.token to viewState.filterToken.second
            ).also { clearAction() }

            Event.SwitchToken -> viewState = viewState.copy(
                filterToken = viewState.filterToken.second to viewState.filterToken.first
            )
        }

    }

}