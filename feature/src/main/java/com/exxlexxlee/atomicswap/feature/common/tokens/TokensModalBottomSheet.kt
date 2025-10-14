package com.exxlexxlee.atomicswap.feature.common.tokens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.exxlexxlee.atomicswap.core.common.ui.CellLazyMultilineSection
import com.exxlexxlee.atomicswap.core.common.ui.HSpacer
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.ListEmptyView
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.core.common.ui.SearchBar
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.common.Badge
import com.exxlexxlee.atomicswap.feature.common.TokenIcon
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TokensModalBottomSheet(
    modifier: Modifier = Modifier,
    title: String,
    onDismissRequest: () -> Unit,
    onClick: (Token) -> Unit,
) {
    val viewModel: TokensViewModel = koinViewModel()
    val viewState by viewModel.viewStates().collectAsState()

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        SearchBar(
            title = title,
            searchHintText = stringResource(com.exxlexxlee.atomicswap.core.common.R.string.search),
            onClose = onDismissRequest,
            onSearchTextChanged = { text -> }
        )

        if (viewState.fullCoins.isEmpty()) {
            ListEmptyView(
                text = stringResource(R.string.tokens_empty_list),
                icon = R.drawable.outline_empty_dashboard_24
            )
        } else {
            CellLazyMultilineSection(
                items = viewState.fullCoins,
                itemContent = { item ->
                    RowUniversal(
                        modifier = Modifier.padding(start = 12.dp),
                        verticalAlignment = Alignment.Bottom,
                        onClick = {
                            onClick(item.tokens.first())
                        },
                    ) {
                        TokenIcon(item.tokens.first())
                        HSpacer(12.dp)
                        Column(modifier = Modifier.weight(1f)) {
                            Row {
                                Text(item.coin.symbol,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                )
                                item.tokens.first().badge()?.let { Badge(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    text = it) }
                            }
                            Row {
                                Text(
                                    text = item.tokens.first().contractAddress?:"",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        item.tokens.forEach { token ->
                            HsIconButton(onClick = {}) {
                                AsyncImage(
                                    model = token.blockchain.iconUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        }

                    }
                }
            )
        }
    }

}