package com.exxlexxlee.atomicswap.feature.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Badge(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.onTertiaryContainer)
            .padding(horizontal = 4.dp),
        text = text,
        color = MaterialTheme.colorScheme.onTertiary,
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
fun TestNetBadge(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(horizontal = 4.dp),
        text = "TESTNET",
        color = MaterialTheme.colorScheme.onTertiary,
        style = MaterialTheme.typography.labelSmall,
    )

}