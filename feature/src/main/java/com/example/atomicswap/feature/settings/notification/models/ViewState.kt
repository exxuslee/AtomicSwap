package com.example.atomicswap.feature.settings.notification.models

import com.example.atomicswap.domain.model.Notification
import com.hwasfy.localize.util.SupportedLocales

data class ViewState(
    val items: Map<String, List<Notification>> = mapOf(),
    val revealCardId: Long = -1,
)