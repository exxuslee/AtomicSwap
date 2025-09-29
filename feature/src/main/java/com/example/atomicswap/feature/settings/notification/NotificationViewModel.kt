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
import com.example.atomicswap.domain.usecases.NotificationReaderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val notificationReaderUseCase: NotificationReaderUseCase
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {
    companion object {
        private const val PAGE_SIZE = 16
    }

    private var pages = 0
    private var pushes = listOf<Notification>()

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.PopBackStack -> viewAction = Action.PopBackStack
            is Event.Delete -> delete(viewEvent.id)
            is Event.MarkAsRead -> markAsRead(viewEvent.id)
            is Event.Reveal -> revealIndex(viewEvent.id)
            is Event.Click -> markAsRead(viewEvent.id)
            Event.BottomReached -> onBottomReached()
        }

    }

    fun sync() = viewModelScope.launch(Dispatchers.IO) {
        pushes = listOf()
        for (i in 0..pages) {
            val items = notificationReaderUseCase.notificationsPaged(i, PAGE_SIZE)
            pushes += items
        }
        viewState = viewState.copy(
            items = pushes.groupBy { DateHelper.formatDate(it.id) },
            revealCardId = -1
        )
    }

    private fun markAsRead(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        notificationReaderUseCase.markAsRead(id)
        sync()
    }

    private fun delete(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        notificationReaderUseCase.delete(id)
        sync()
    }

    @Synchronized
    private fun onBottomReached() = viewModelScope.launch(Dispatchers.IO) {
        val newItems = notificationReaderUseCase.notificationsPaged(pages + 1, PAGE_SIZE)
        Log.d("Notifications", "onBottomReached() ${newItems.size}")
        if (newItems.isEmpty()) return@launch
        pages++
        pushes += newItems
        viewState = viewState.copy(items = pushes.groupBy { DateHelper.formatDate(it.id) })
    }

    private fun revealIndex(id: Long) {
        viewState = viewState.copy(revealCardId = id)
    }

}