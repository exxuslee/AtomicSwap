package com.example.atomicswap.core.common.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.common.R

@Composable
fun HsRow(
    @DrawableRes iconRes: Int,
    titleContent: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
    arrowRight: Boolean = false,
    valueContent: (@Composable () -> Unit)? = null,
) {
    RowUniversal(modifier = Modifier.padding(horizontal = 12.dp), onClick = onClick) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.outlineVariant),
        )
        titleContent()
        Spacer(Modifier.weight(1f))
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
            tint = MaterialTheme.colorScheme.outlineVariant
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