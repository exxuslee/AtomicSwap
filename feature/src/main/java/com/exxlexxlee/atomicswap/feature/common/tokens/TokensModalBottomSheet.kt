package com.exxlexxlee.atomicswap.feature.common.tokens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.SearchBar
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Action
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Event
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
    val viewAction by viewModel.viewActions().collectAsState(null)

    LaunchedEffect(title) {
        viewModel.obtainEvent(Event.Title(title))
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        val searchTitle = if (viewState.isTokenView) viewState.title
        else stringResource(R.string.select_chain)
        var searchMode by remember { mutableStateOf(false) }
        SearchBar(
            title = searchTitle,
            searchHintText = stringResource(com.exxlexxlee.atomicswap.core.common.R.string.search),
            searchMode = searchMode,
            onClose = {
                if (viewState.isTokenView) onDismissRequest.invoke()
                else viewModel.obtainEvent(Event.OnTokenView)
            },
            onCloseSearch = {
                searchMode = false
            },
            onSearchTextChanged = { text ->
                viewModel.obtainEvent(Event.Filter(text))
            },
        ) {
            if (viewState.isTokenView) {
                HsIconButton(onClick = { searchMode = true }) {
                    Icon(
                        painter = painterResource(com.exxlexxlee.atomicswap.core.common.R.drawable.outline_search_24),
                        contentDescription = stringResource(com.exxlexxlee.atomicswap.core.common.R.string.search),
                    )
                }
                HsIconButton(
                    onClick = { viewModel.obtainEvent(Event.OnTokenView) },
                ) {
                    Icon(
                        painter = painterResource(com.exxlexxlee.atomicswap.core.common.R.drawable.outline_category_24),
                        contentDescription = stringResource(com.exxlexxlee.atomicswap.core.common.R.string.blockchain),
                    )
                }
            }
        }

        if (viewState.isTokenView) TokensView(viewState) {
            viewModel.obtainEvent(it)
        } else ChainsView(viewState) {
            viewModel.obtainEvent(it)
        }
    }

    when (viewAction) {
        Action.OnDismissRequest -> {
            onDismissRequest()
            viewModel.clearAction()
        }

        is Action.OnSelectToken -> {
            onClick((viewAction as Action.OnSelectToken).token)
            viewModel.clearAction()
        }

        null -> {}
    }

}