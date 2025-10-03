package com.exxlexxlee.atomicswap.feature.tabs.settings.aggregator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun AggregatorScreen(
    viewModel: AggregatorViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    AggregatorView(viewState) {
        viewModel.obtainEvent(it)
    }
}