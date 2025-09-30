package com.exxlexxlee.atomicswap.feature.settings.main

import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.domain.usecases.AggregatorUseCase
import com.exxlexxlee.atomicswap.domain.usecases.NotificationReaderUseCase
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.domain.usecases.ThemeController
import com.exxlexxlee.atomicswap.feature.settings.main.models.Action
import com.exxlexxlee.atomicswap.feature.settings.main.models.Event
import com.exxlexxlee.atomicswap.feature.settings.main.models.ViewState
import com.reown.appkit.client.Modal
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themeController: ThemeController,
    private val notificationReaderUseCase: NotificationReaderUseCase,
    private val aggregatorUseCase: AggregatorUseCase,
    private val settingsUseCase: SettingsUseCase,
    private val walletConnectManager: WalletConnectManager,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        isTermsOfUseRead = settingsUseCase.isTermsOfUseRead(),
    )
) {
    private val avatarGenerator = AvatarGenerator()

    init {
        viewModelScope.launch {
            combine(
                notificationReaderUseCase.unreadCount,
                walletConnectManager.delegate.connectionState,
                aggregatorUseCase.selected,
                themeController.isDark,
                settingsUseCase.isTermsOfUseRead,
            ) { unreadCount, connectionState, selectedAggregator, isDark, isTermsOfUseRead ->
                ViewState(
                    isTermsOfUseRead = isTermsOfUseRead,
                    avatar = avatarGenerator.generateIdenticonBitmap("0", 360),
                    isDark = isDark,
                    unreadCount = unreadCount,
                    isWalletConnect = connectionState.isAvailable,
                    priceAggregator = selectedAggregator,
                    isEmptyLocalStorage = isClearLocalStorage(),
                )
            }.collect { newState ->
                viewState = newState
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.IsDark -> {
                themeController.setDark(viewEvent.newValue)
                viewState = viewState.copy(isDark = viewEvent.newValue)
            }

            Event.OpenWalletConnectDialog -> viewAction = Action.ConnectWcDialog
            Event.OpenTermsScreen -> viewAction = Action.OpenTermsScreen
            Event.OpenLanguageScreen -> viewAction = Action.OpenLanguageScreen
            Event.OpenNotificationScreen -> viewAction = Action.OpenNotificationScreen
            Event.OpenAboutScreen -> viewAction = Action.OpenAboutScreen
            Event.OpenDonateScreen -> viewAction = Action.OpenDonateScreen
            Event.OpenClearStorage -> viewAction = Action.LocaleStorageDialog
            Event.OpenAggregatorScreen -> viewAction = Action.OpenAggregatorScreen
            Event.ConfirmClearStorage -> {
                viewModelScope.launch {
                    notificationReaderUseCase.deleteAll()
                    clearAction()
                }
            }
        }

    }

    private suspend fun isClearLocalStorage(): Boolean {
        val notify = notificationReaderUseCase.unreadCount.firstOrNull() == 0
        val wc =
            walletConnectManager.delegate.connectionState.firstOrNull() == Modal.Model.ConnectionState(
                false
            )
        return notify && wc
    }
}