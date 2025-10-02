package com.exxlexxlee.atomicswap.feature.chronicle.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.model.SwapState
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.Event
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.SwapFilterState
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.ViewState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChronicleView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = viewState.selectedTab.ordinal,
                modifier = Modifier.fillMaxWidth()
            ) {
                SwapFilterState.values().forEach { filterState ->
                    Tab(
                        selected = viewState.selectedTab == filterState,
                        onClick = { eventHandler(Event.SelectTab(filterState)) },
                        text = {
                            Text(
                                text = getTabTitle(filterState),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (viewState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (viewState.filteredSwaps.isEmpty()) {
                Text(
                    text = "No swaps found",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewState.filteredSwaps) { swap ->
                        SwapItem(
                            swap = swap,
                            onClick = { eventHandler(Event.OpenSwap(swap.swapId)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SwapItem(
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
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Swap ID: ${swap.swapId.take(8)}...",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = getSwapStateText(swap.swapState),
                    style = MaterialTheme.typography.labelMedium,
                    color = getSwapStateColor(swap.swapState)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${swap.makerToken.symbol} → ${swap.takerToken.symbol}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = formatDate(swap.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Amount: ${swap.makerExactAmount} ${swap.makerToken.symbol}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun getTabTitle(filterState: SwapFilterState): String {
    return when (filterState) {
        SwapFilterState.ALL -> "All"
        SwapFilterState.ACTIVE -> "Active"
        SwapFilterState.REDEEM -> "Redeem"
        SwapFilterState.REFUND -> "Refund"
    }
}

private fun getSwapStateText(swapState: SwapState): String {
    return when (swapState) {
        SwapState.REQUESTED -> "Requested"
        SwapState.RESPONDED -> "Responded"
        SwapState.INITIATOR_BAILED -> "Initiator Bailed"
        SwapState.RESPONDER_BAILED -> "Responder Bailed"
        SwapState.INITIATOR_REDEEMED -> "Initiator Redeemed"
        SwapState.RESPONDER_REDEEMED -> "Responder Redeemed"
        SwapState.REFUNDED -> "Refunded"
    }
}

@Composable
private fun getSwapStateColor(swapState: SwapState): Color {
    return when (swapState) {
        SwapState.REQUESTED, SwapState.RESPONDED -> MaterialTheme.colorScheme.primary
        SwapState.INITIATOR_BAILED, SwapState.RESPONDER_BAILED -> MaterialTheme.colorScheme.tertiary
        SwapState.INITIATOR_REDEEMED, SwapState.RESPONDER_REDEEMED -> Color(0xFF4CAF50)
        SwapState.REFUNDED -> MaterialTheme.colorScheme.error
    }
}

private fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(date)
}

@Preview
@Composable
fun ChronicleView_Preview() {
    AppTheme {
        ChronicleView(
            viewState = ViewState(),
            eventHandler = {},
        )
    }
}