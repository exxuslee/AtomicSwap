package com.exxlexxlee.atomicswap.feature.tabs.chronicle.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun AllChronicleScreen(
    viewModel: AllChronicleViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    AllChronicleView(viewState) {
        viewModel.obtainEvent(it)
    }

}
