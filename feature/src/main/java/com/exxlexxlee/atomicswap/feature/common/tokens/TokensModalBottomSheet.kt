package com.exxlexxlee.atomicswap.feature.common.tokens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.atomicswap.core.common.ui.SearchBar
import com.exxlexxlee.atomicswap.core.swap.model.Token
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
        SearchBar(
            title = viewState.title,
            searchHintText = stringResource(com.exxlexxlee.atomicswap.core.common.R.string.search),
            onClose = onDismissRequest,
            onSearchTextChanged = { text ->
                viewModel.obtainEvent(Event.Filter(text))
            },
            onChainFilter = { viewModel.obtainEvent(Event.OnTokenView) },
        )

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