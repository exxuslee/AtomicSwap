package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.PrimaryIndicator
import androidx.compose.material3.TabRowDefaults.primaryContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.navigation.animatedComposable
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.BadgeType
import com.exxlexxlee.atomicswap.core.common.ui.BadgedIcon
import com.exxlexxlee.atomicswap.core.common.ui.ListEmptyView
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.model.SwapState
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.navigation.Routes
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.ViewState
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake.MyMakeChronicleScreen
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.ActiveChronicleScreen
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.confirmed.ConfirmedChronicleScreen
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded.RefundedChronicleScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChronicleView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    val navController = rememberNavController()
    Column {
        PrimaryTabRow(
            selectedTabIndex = viewState.selectedTab.pos,
            indicator = {
                PrimaryIndicator(
                    color = primaryContentColor,
                    modifier = Modifier
                        .tabIndicatorOffset(
                            viewState.selectedTab.pos,
                            matchContentSize = false
                        ),
                    width = Dp.Unspecified,
                )
            },
        ) {
            FilterStateChronicle.values.forEach { filterState ->
                Tab(
                    selected = viewState.selectedTab == filterState,
                    onClick = {
                        eventHandler(Event.SelectTab(filterState))
                        val tab = when (filterState) {
                            FilterStateChronicle.Active -> Routes.ChronicleRoute.ActiveRoute.route
                            FilterStateChronicle.Complete -> Routes.ChronicleRoute.CompleteRoute.route
                            FilterStateChronicle.MyMake -> Routes.ChronicleRoute.MyMakeRoute.route
                            FilterStateChronicle.Refund -> Routes.ChronicleRoute.RefundRoute.route
                        }
                        navController.navigate(tab) {
                            launchSingleTop = true
                            restoreState = true
                        }
                              },
                    icon = {
                        BadgedIcon(
                            badge = BadgeType.BadgeNumber(3)
                        ) {
                            Icon(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                painter = painterResource(filterState.icon),
                                contentDescription = filterState.pos.toString()
                            )
                        }
                    },
                )
            }
        }

        NavHost(
            navController = navController,
            startDestination = Routes.ChronicleRoute.MyMakeRoute.route,
            modifier = Modifier.fillMaxSize()
        ) {
            animatedComposable(Routes.ChronicleRoute.MyMakeRoute.route) {
                MyMakeChronicleScreen()
            }
            animatedComposable(Routes.ChronicleRoute.ActiveRoute.route) {
                ActiveChronicleScreen()
            }
            animatedComposable(Routes.ChronicleRoute.CompleteRoute.route) {
                ConfirmedChronicleScreen()
            }
            animatedComposable(Routes.ChronicleRoute.RefundRoute.route) {
                RefundedChronicleScreen()
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
                    text = "${swap.make.makerExactAmount} ${swap.make.makerToken.coin.symbol} → " +
                            "${swap.make.takerExactAmount} ${swap.make.takerToken.coin.symbol}",
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
private fun BoxScope.TokenIcon(url: String, modifier: Modifier = Modifier) {
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
        SwapState.REQUESTED, SwapState.RESPONDED, SwapState.INITIATOR_BAILED, SwapState.RESPONDER_BAILED -> {
            text = "Waiting"
            containerColor = MaterialTheme.colorScheme.secondary
        }

        SwapState.INITIATOR_REDEEMED, SwapState.RESPONDER_REDEEMED -> {
            text = "Completed"
            containerColor = Color(0xFF4CAF50)
        }

        SwapState.REFUNDED -> {
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