package com.exxlexxlee.atomicswap.core.common.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

sealed class BadgeType {
    object BadgeDot : BadgeType()
    class BadgeNumber(val number: Int) : BadgeType()

    companion object {
        fun fromInt(number: Int?): BadgeType? = number?.let {
            when (it) {
                0 -> BadgeDot
                else -> BadgeNumber(it)
            }
        }
    }
}

@Composable
fun BadgedIcon(
    badge: BadgeType?,
    icon: @Composable BoxScope.() -> Unit,
) {
    when (badge) {
        is BadgeType.BadgeNumber ->
            BadgedBox(
                badge = {
                    Badge {
                        Text(
                            text = badge.number.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondary,
                        )
                    }
                },
                content = icon
            )

        BadgeType.BadgeDot ->
            BadgedBox(
                badge = { Badge() },
                content = icon
            )

        else -> {
            Box { icon() }
        }
    }
}