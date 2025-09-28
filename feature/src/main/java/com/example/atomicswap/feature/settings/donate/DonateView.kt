package com.example.atomicswap.feature.settings.donate

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.feature.R
import com.example.atomicswap.core.common.theme.AppTheme
import com.example.atomicswap.core.common.ui.HeaderStick
import com.example.atomicswap.core.common.ui.TopAppBar
import com.example.atomicswap.feature.settings.donate.models.Event
import com.example.atomicswap.feature.settings.donate.models.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TopAppBar("Donate") { eventHandler.invoke(Event.PopBackStack) }

        val clipboard = LocalClipboardManager.current
        val uriHandler = LocalUriHandler.current

        LazyColumn {
            item {
                Text(
                    text = "If you like the app, consider supporting development.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            stickyHeader {
                DonationAmountSelector(
                    selectedAmount = viewState.selectedAmount,
                    availableAmounts = viewState.availableAmounts,
                    onAmountSelected = { amount -> eventHandler(Event.OnAmountSelected(amount)) }
                )
            }

            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    text = "Selected: ${viewState.selectedAmount} USDT",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
            }

            item {
                AddressRow(
                    label = "BTC",
                    address = "bc1q-example-btc-address",
                    onCopy = { clipboard.setText(AnnotatedString("bc1q-example-btc-address")) }
                )
            }

            item {
                AddressRow(
                    label = "ETH",
                    address = "0xExampleEthAddress000000000000000000",
                    onCopy = { clipboard.setText(AnnotatedString("0xExampleEthAddress000000000000000000")) }
                )
            }

            item {
                AddressRow(
                    label = "USDT (TRC20)",
                    address = "TVExampleTronAddress000000000000000",
                    onCopy = { clipboard.setText(AnnotatedString("TVExampleTronAddress000000000000000")) }
                )
            }

            stickyHeader {
                HeaderStick("Other")
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Buy me a coffee",
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text = "Quick one-time tip via web",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    Button(onClick = { uriHandler.openUri("https://buymeacoffee.com/") }) {
                        Text("Open")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Thank you for your support!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun AddressRow(
    label: String,
    address: String,
    onCopy: () -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.padding(start = 8.dp))
            TextButton(onClick = onCopy) {
                Text("Copy")
            }
        }
    }
}

@Composable
private fun DonationAmountSelector(
    availableAmounts: List<Pair<Int, Int>>,
    selectedAmount: Int,
    onAmountSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        availableAmounts.forEach { amount ->
            DonateButton(
                amount = amount.second,
                iconId = amount.first,
                isSelected = amount.second == selectedAmount,
                onClick = { onAmountSelected(amount.second) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DonateButton(
    amount: Int,
    @DrawableRes iconId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painterResource(id = iconId),
                    contentDescription = amount.toString()
                )
            }
        }
    }
}

@Preview
@Composable
fun DonateView_Preview() {
    AppTheme {
        DonateView(
            viewState = ViewState(),
            eventHandler = {}
        )
    }
}
