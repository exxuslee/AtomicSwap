package com.exxlexxlee.atomicswap.feature.common.tokens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.ui.CellLazyMultilineSection
import com.exxlexxlee.atomicswap.core.common.ui.HsRow
import com.exxlexxlee.atomicswap.core.common.ui.ListEmptyView
import com.exxlexxlee.atomicswap.core.common.ui.SearchBar
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.feature.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TokensModalBottomSheet(
    modifier: Modifier = Modifier,
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
            title = stringResource(R.string.app),
            searchHintText = stringResource(com.exxlexxlee.atomicswap.core.common.R.string.search),
            onClose = onDismissRequest,
            onSearchTextChanged = { text -> }
        )

        if (viewState.tokens.isEmpty()) {
            ListEmptyView(
                text = stringResource(R.string.tokens_empty_list),
                icon = R.drawable.outline_empty_dashboard_24
            )
        } else {
            CellLazyMultilineSection(
                items = viewState.tokens,
                itemContent = { item ->
                    HsRow(
                        icon = ImageVector.vectorResource(R.drawable.outline_wallet_24),
                        onClick = {
                            onClick(item)
                        },
                        titleContent = {
                            Text(
                                stringResource(R.string.wallet_connect),
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        },
                        arrowRight = false,
                    )
                }
            )
        }
    }

}