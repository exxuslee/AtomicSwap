package com.exxlexxlee.atomicswap.feature.settings.donate

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.CellUniversalSection
import com.exxlexxlee.atomicswap.core.common.ui.HSpacer
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.HsRow
import com.exxlexxlee.atomicswap.core.common.ui.TopAppBar
import com.exxlexxlee.atomicswap.core.common.ui.VSpacer
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.settings.donate.models.Event
import com.exxlexxlee.atomicswap.feature.settings.donate.models.ViewState
import com.reown.android.internal.common.scope
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
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(stringResource(id = R.string.donate)) { eventHandler.invoke(Event.PopBackStack) }

        val clipboard = LocalClipboard.current
        val donates = remember {
            listOf(
                DonateViewItem("Bitcoin", "36b5Z19fLrbgEcV1dwhwiFjix86bGweXKC"),
                DonateViewItem("Ethereum || BSC", "0x6F1C4B2bd0489e32AF741C405CcA696E8a95ce9C"),
                DonateViewItem("Solana", "2zMufqDhhiMbcQRVLiAVrBv9SWdHvxrHgAsdQfMbUaJS"),
                DonateViewItem("Tron", "TKQMJN2aFAyPwaFCdg3AxhRT9xqsRuTvb3"),
            )
        }

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.verticalScroll(scrollState),
        ) {
            Text(
                modifier = Modifier.padding(12.dp,0.dp,12.dp,12.dp,),
                text = stringResource(R.string.donate_header_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            DonationAmountSelector(
                selectedAmount = viewState.selectedAmount,
                availableAmounts = viewState.availableAmounts,
                onAmountSelected = { amount -> eventHandler(Event.OnAmountSelected(amount)) }
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.donate_selected_prefix),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontFamily = FontFamily.Monospace)) {
                            append(viewState.selectedAmount.toString().padStart(6))
                        }
                        append(" ")
                        append(stringResource(R.string.donate_currency_usdt))
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.End
                )
            }

            CellUniversalSection(
                donates.mapIndexed { index, donat ->
                    {
                        HsRow(
                            iconRes = R.drawable.outline_database_off_24,
                            titleContent = {
                                Column(
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                ) {
                                    Text(
                                        text = donat.chain,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                    Text(
                                        text = donat.address,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                }
                            },
                            onClick = {
                                eventHandler.invoke(Event.OnTokenSelected(index))
                            },
                            onSelect = index == viewState.selectedToken,
                            arrowRight = false,
                        ) {
                            HsIconButton({
                                scope.launch {
                                    clipboard.setClipEntry(
                                        ClipEntry(
                                            ClipData.newPlainText(donat.chain, donat.address)
                                        )
                                    )
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.outline_content_copy_24),
                                    contentDescription = stringResource(R.string.donate_copy_cd),
                                )
                            }
                        }
                    }
                }
            )
        }

        VSpacer(24.dp)
        TextButton(
            onClick = {},
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.height(36.dp)
        ) {
            Text(
                stringResource(R.string.donate).uppercase(),
                maxLines = 1,
                modifier = Modifier.padding(horizontal = 36.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))
        if (viewState.isAddressCopied) Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.donate_footer_thanks),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(0.5f))
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
            .padding(horizontal = 12.dp),
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
