package com.exxlexxlee.atomicswap.feature.tabs.book.makes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.atomicswap.core.common.R
import com.exxlexxlee.atomicswap.feature.tabs.book.makes.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.book.makes.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.common.tokens.TokensModalBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun MakesBookScreen(
    viewModel: MakesViewModel = koinViewModel(),
) {
	val viewState by viewModel.viewStates().collectAsState()
	val viewAction by viewModel.viewActions().collectAsState(null)

    MakesView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {
        Action.MakerToken -> TokensModalBottomSheet(
            title = stringResource(R.string.to),
            onDismissRequest = {
                viewModel.obtainEvent(Event.ClearAction)
            }) {
            viewModel.obtainEvent(Event.MakerToken(it))
        }

        Action.TakerToken -> TokensModalBottomSheet(
            title = stringResource(R.string.from),
            onDismissRequest = {
                viewModel.obtainEvent(Event.ClearAction)
            }) {
            viewModel.obtainEvent(Event.TakerToken(it))
        }

        null -> {}
    }

}
