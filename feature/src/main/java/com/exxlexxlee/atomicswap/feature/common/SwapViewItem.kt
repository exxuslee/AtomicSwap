package com.exxlexxlee.atomicswap.feature.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.model.SwapState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SwapViewItem(
    swap: Swap,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TokenPairIcon(
                makerIconUrl = swap.make.makerToken.coin.iconUrl,
                takerIconUrl = swap.make.takerToken.coin.iconUrl,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ID: ${swap.swapId.take(8)}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    StatusChip(state = swap.swapState)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${swap.make.makerExactAmount} ${swap.make.makerToken.coin.symbol} → "
                            + "${swap.make.takerExactAmount} ${swap.make.takerToken.coin.symbol}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatDateTime(swap.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


private fun formatDateTime(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    return formatter.format(date)
}

@Composable
private fun TokenPairIcon(
    makerIconUrl: String,
    takerIconUrl: String,
) {
    Box(modifier = Modifier.size(44.dp)) {
        TokenIcon(url = makerIconUrl, modifier = Modifier.align(Alignment.CenterStart))
        TokenIcon(url = takerIconUrl, modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
private fun TokenIcon(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier.size(32.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun StatusChip(state: SwapState) {
    val text: String
    val containerColor: Color
    val labelColor: Color = MaterialTheme.colorScheme.onSecondary

    when (state) {
        SwapState.Requested, SwapState.Responded, SwapState.InitiatorBailed, SwapState.ResponderBailed -> {
            text = "Waiting"
            containerColor = MaterialTheme.colorScheme.secondary
        }

        SwapState.InitiatorRedeemed, SwapState.ResponderRedeemed -> {
            text = "Completed"
            containerColor = Color(0xFF4CAF50)
        }

        SwapState.Refunded -> {
            text = "Expired"
            containerColor = MaterialTheme.colorScheme.tertiary
        }
    }

    AssistChip(
        onClick = {},
        enabled = false,
        label = { Text(text = text, color = labelColor) },
        colors = AssistChipDefaults.assistChipColors(
            disabledContainerColor = containerColor,
            disabledLabelColor = labelColor
        )
    )
}