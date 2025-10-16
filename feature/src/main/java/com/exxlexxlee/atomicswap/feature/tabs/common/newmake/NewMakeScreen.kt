package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.atomicswap.core.common.R
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.common.tokens.TokensModalBottomSheet
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NewMakeScreen(
    makeId: String,
    viewModel: NewMakeViewModel = koinViewModel{ parametersOf(makeId) },
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    NewMakeView(viewState) {
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