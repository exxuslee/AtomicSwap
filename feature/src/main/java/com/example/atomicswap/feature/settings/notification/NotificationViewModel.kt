package com.example.atomicswap.feature.settings.notification

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.atomicswap.core.common.base.BaseViewModel
import com.example.atomicswap.domain.model.Notification
import com.example.atomicswap.domain.repository.NotificationRepository
import com.example.atomicswap.feature.settings.notification.models.Action
import com.example.atomicswap.feature.settings.notification.models.Event
import com.example.atomicswap.feature.settings.notification.models.ViewState
import com.example.atomicswap.core.common.base.DateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository.Reader
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {
    companion object {
        private const val PAGE_SIZE = 16
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.PopBackStack -> viewAction = Action.PopBackStack
            is Event.Delete -> TODO()
            is Event.MarkAsRead -> TODO()
            is Event.RevealCardIndex -> TODO()
            is Event.OnClick -> TODO()
            Event.OnBottomReached -> onBottomReached()
        }

    }

    private var pages = 0
    private var pushes = listOf<Notification>()

    @Synchronized
    fun onBottomReached() = viewModelScope.launch(Dispatchers.IO)  {
        val newItems = repository.notificationsPaged(pages + 1, PAGE_SIZE)
        Log.d("Notifications", "onBottomReached() ${newItems.size}")
        if (newItems.isEmpty()) return@launch
        pages++
        pushes += newItems
        viewState = viewState.copy(items = pushes.groupBy { DateHelper.formatDate(it.id) })
    }

}