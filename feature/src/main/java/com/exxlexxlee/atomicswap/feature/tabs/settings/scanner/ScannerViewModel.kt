package com.exxlexxlee.atomicswap.feature.tabs.settings.scanner

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.tabs.settings.scanner.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.settings.scanner.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.settings.scanner.models.ViewState


class ScannerViewModel(
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.PermissionState -> viewState =
                viewState.copy(hasCameraPermission = viewEvent.isGranted)

            Event.RequestPermission -> viewAction = Action.RequestPermission
        }

    }


}