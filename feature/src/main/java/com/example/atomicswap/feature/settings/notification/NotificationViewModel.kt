package com.example.atomicswap.feature.settings.notification

import android.content.Context
import android.provider.Settings.Global.getString
import com.example.atomicswap.core.ui.base.BaseViewModel
import com.example.atomicswap.feature.settings.notification.models.Action
import com.example.atomicswap.feature.settings.notification.models.Event
import com.example.atomicswap.feature.settings.notification.models.ViewState
import com.hwasfy.localize.util.DateHelper
import java.util.Calendar
import java.util.Date

class NotificationViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.PopBackStack -> viewAction = Action.PopBackStack
            is Event.Delete -> TODO()
            is Event.MarkAsRead -> TODO()
            is Event.RevealCardIndex -> TODO()
        }

    }

}