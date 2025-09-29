package com.example.atomicswap.feature.settings.donate

import android.content.ClipData
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.common.theme.AppTheme
import com.example.atomicswap.core.common.ui.HsIconButton
import com.example.atomicswap.core.common.ui.TopAppBar
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.donate.models.Event
import com.example.atomicswap.feature.settings.donate.models.ViewState
import kotlinx.coroutines.launch

private data class DonateViewItem(
    val chain: String,
    val address: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonateView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TopAppBar("Donate") { eventHandler.invoke(Event.PopBackStack) }

        val clipboard = LocalClipboard.current
        val scope = rememberCoroutineScope()
        val uriHandler = LocalUriHandler.current
        val donates = remember {
            listOf(
                DonateViewItem("Bitcoin blockchain", "bc1q-example-btc-address"),
                DonateViewItem(
                    "Ethereum blockchain | BSC blockchain",
                    "0xExampleEthAddress000000000000000000"
                ),
                DonateViewItem("Solana blockchain", "bc1q-example-btc-address"),
                DonateViewItem("Tron blockchain", "bc1q-example-btc-address"),
            )
        }

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
                    text = buildAnnotatedString {
                        append("Selected: ")
                        withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                            append(viewState.selectedAmount.toString().padStart(5))
                        }
                        append(" USDT")
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
            }

            items(donates) { donat ->
                DonateRow(
                    label = donat.chain,
                    address = donat.address,
                    onCopy = {
                        scope.launch {
                            clipboard.setClipEntry(ClipEntry(ClipData.newPlainText("Copy address", donat.address)))
                        }
                    },
                    onDonate = {
                        scope.launch {
                            clipboard.setClipEntry(ClipEntry(ClipData.newPlainText("Copy address", donat.address)))
                        }
                    },
                )
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
private fun DonateRow(
    label: String,
    address: String,
    onCopy: () -> Unit,
    onDonate: () -> Unit,
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .weight(1f),
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = address,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    HsIconButton(onCopy) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_content_copy_24),
                            contentDescription = "copy",
                        )
                    }
                }
            }
        }

        TextButton(
            onClick = onDonate,
            colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.height(36.dp)
        ) {
            Text(
                "DONATE",
                maxLines = 1
            )
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
            DonateAmountButton(
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
private fun DonateAmountButton(
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
