package com.exxlexxlee.atomicswap.feature.settings.notification.models

import com.exxlexxlee.atomicswap.domain.model.Notification
import com.hwasfy.localize.util.SupportedLocales

data class ViewState(
    val items: Map<String, List<Notification>> = mapOf(),
    val revealCardId: Long = -1,
)