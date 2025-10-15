package com.exxlexxlee.atomicswap.feature.tabs.book.make

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.atomicswap.core.common.R
import com.exxlexxlee.atomicswap.feature.common.tokens.TokensModalBottomSheet
import com.exxlexxlee.atomicswap.feature.tabs.book.make.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.book.make.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.make.models.Event.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun MakeBookScreen(
    viewModel: MakeViewModel = koinViewModel(),
) {
	val viewState by viewModel.viewStates().collectAsState()
	val viewAction by viewModel.viewActions().collectAsState(null)

    MakeView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {
        Action.MakerToken -> TokensModalBottomSheet(
            title = stringResource(R.string.to),
            onDismissRequest = {
                viewModel.obtainEvent(Event.ClearAction)
            }) {
            viewModel.obtainEvent(MakerToken(it))
        }

        Action.TakerToken -> TokensModalBottomSheet(
            title = stringResource(R.string.from),
            onDismissRequest = {
                viewModel.obtainEvent(Event.ClearAction)
            }) {
            viewModel.obtainEvent(TakerToken(it))
        }

        null -> {}
    }

}
