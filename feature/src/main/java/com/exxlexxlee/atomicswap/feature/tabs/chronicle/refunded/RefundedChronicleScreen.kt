package com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun RefundedChronicleScreen(
    viewModel: RefundedViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    RefundedView(viewState) {
        viewModel.obtainEvent(it)
    }

}
