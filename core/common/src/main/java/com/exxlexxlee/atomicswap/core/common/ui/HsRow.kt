package com.exxlexxlee.atomicswap.core.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.R

@Composable
fun HsRow(
    @DrawableRes iconRes: Int,
    titleContent: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
    onSelect: Boolean = false,
    arrowRight: Boolean = false,
    valueContent: (@Composable () -> Unit)? = null,
) {
    RowUniversal(
        modifier = Modifier
            .background(
                if (onSelect) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
            )
            .padding(horizontal = 12.dp),
        onClick = onClick,
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null,
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            titleContent()
        }

        valueContent?.invoke()
        if (arrowRight) Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
        )
    }
}

@Composable
fun HsRow(
    icon: ImageVector,
    titleContent: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
    arrowRight: Boolean = false,
    valueContent: (@Composable () -> Unit)? = null,
) {
    RowUniversal(modifier = Modifier.padding(horizontal = 12.dp), onClick = onClick) {
        Icon(
            icon,
            modifier = Modifier.size(30.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
        titleContent()
        Spacer(Modifier.weight(1f))
        if (valueContent != null) valueContent()

        if (arrowRight) Image(
            modifier = Modifier.size(20.dp),
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
        )
    }
}