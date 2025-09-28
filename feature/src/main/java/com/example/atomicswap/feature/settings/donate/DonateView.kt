package com.example.atomicswap.feature.settings.donate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.common.theme.AppTheme
import com.example.atomicswap.core.common.ui.HeaderStick
import com.example.atomicswap.feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateView(onBack: () -> Unit) {
    Column {
        TopAppBar(
            windowInsets = WindowInsets(0, 0, 0, 0),
            title = {
                Text(
                    text = "Donate",
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        )

        val clipboard = LocalClipboardManager.current
        val uriHandler = LocalUriHandler.current

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = "If you like the app, consider supporting development.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            stickyHeader {
                HeaderStick("Crypto")
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
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    Button(onClick = { uriHandler.openUri("https://buymeacoffee.com/") }) {
                        Text("Open")
                    }
                }
            }

            item {
                Text(
                    text = "Thank you for your support!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
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

@Preview
@Composable
fun DonateView_Preview() {
    AppTheme {
        DonateView(
            onBack = {}
        )
    }
}
