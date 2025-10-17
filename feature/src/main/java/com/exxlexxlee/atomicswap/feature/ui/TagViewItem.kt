package com.exxlexxlee.atomicswap.feature.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.HSpacer
import com.exxlexxlee.atomicswap.core.common.ui.VSpacer
import com.exxlexxlee.atomicswap.feature.R

@Composable
fun TagViewItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    enabled: Boolean,
    textColor: Color = MaterialTheme.colorScheme.primary,
    iconEnd: Painter? = null,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary
            )
            HSpacer(8.dp)
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                textAlign = TextAlign.Center,
            )
            iconEnd?.let {
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = icon,
                    contentDescription = text,
                    tint = textColor
                )
            }
        }
    }
}

@Preview
@Composable
private fun TagViewItemPreview() {
    AppTheme {
        Column {
            TagViewItem(
                icon = ImageVector.vectorResource(R.drawable.outline_wallet_24),
                text = "1 BNB = 1000 USDT",
                enabled = true,
            )
            VSpacer(12.dp)
            TagViewItem(
                icon = ImageVector.vectorResource(R.drawable.outline_wallet_24),
                text = "1 BNB = 1000 USDT",
                textColor = MaterialTheme.colorScheme.error,
                iconEnd = painterResource(R.drawable.outline_wallet_24),
                enabled = false,
            )
        }

    }
}