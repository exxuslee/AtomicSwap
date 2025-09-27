package com.example.atomicswap.core.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class BadgeType {
    object BadgeDot : BadgeType()
    class BadgeNumber(val number: Int) : BadgeType()
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
                badge = {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) { }
                },
                content = icon
            )

        else -> {
            Box { icon() }
        }
    }
}