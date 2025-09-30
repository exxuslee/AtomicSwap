package com.exxlexxlee.atomicswap.feature.settings.aggregator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.feature.settings.aggregator.models.Action
import org.koin.androidx.compose.koinViewModel

@Composable
fun AggregatorScreen(
    viewModel: AggregatorViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val navController = LocalNavController.current

    AggregatorView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {
        is Action.PopBackStack -> {
            viewModel.clearAction()
            navController.popBackStack()
        }
        null -> {}
    }
}