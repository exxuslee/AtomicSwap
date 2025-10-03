package com.exxlexxlee.atomicswap.feature.tabs.settings.notification.models

import com.exxlexxlee.atomicswap.domain.model.Notification

data class ViewState(
    val items: Map<String, List<Notification>> = mapOf(),
    val revealCardId: Long = -1,
)