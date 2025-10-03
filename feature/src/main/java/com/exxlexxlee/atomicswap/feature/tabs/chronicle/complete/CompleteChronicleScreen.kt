package com.exxlexxlee.atomicswap.feature.tabs.chronicle.complete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfirmedChronicleScreen(
    viewModel: CompleteViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    CompleteView(viewState) {
        viewModel.obtainEvent(it)
    }

}