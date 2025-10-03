package com.exxlexxlee.atomicswap.feature.tabs.chronicle.confirmed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfirmedChronicleScreen(
    viewModel: ConfirmedViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    ConfirmedView(viewState) {
        viewModel.obtainEvent(it)
    }

}