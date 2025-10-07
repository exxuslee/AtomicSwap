package com.exxlexxlee.atomicswap.feature.tabs.settings.notification

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.core.common.base.DateHelper
import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.domain.usecases.PushReaderUseCase
import com.exxlexxlee.atomicswap.feature.tabs.settings.notification.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.settings.notification.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.settings.notification.models.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val pushReaderUseCase: PushReaderUseCase
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {
    companion object {
        private const val PAGE_SIZE = 16
    }

    private var pages = 0
    private var pushes = listOf<Notification>()

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
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
            val items = pushReaderUseCase.notificationsPaged(i, PAGE_SIZE)
            pushes += items
        }
        viewState = viewState.copy(
            items = pushes.groupBy { DateHelper.formatDate(it.id) },
            revealCardId = -1
        )
    }

    private fun markAsRead(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        pushReaderUseCase.markAsRead(id)
        sync()
    }

    private fun delete(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        pushReaderUseCase.delete(id)
        sync()
    }

    @Synchronized
    private fun onBottomReached() = viewModelScope.launch(Dispatchers.IO) {
        val newItems = pushReaderUseCase.notificationsPaged(pages + 1, PAGE_SIZE)
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