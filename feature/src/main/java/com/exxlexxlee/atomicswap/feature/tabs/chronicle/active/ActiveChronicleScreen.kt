package com.exxlexxlee.atomicswap.feature.tabs.chronicle.active

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveChronicleScreen(
    viewModel: ActiveViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    ActiveView(viewState) {
        viewModel.obtainEvent(it)
    }

}
