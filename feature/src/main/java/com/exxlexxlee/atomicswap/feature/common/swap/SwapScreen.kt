package com.exxlexxlee.atomicswap.feature.common.swap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SwapScreen(
    swapId: String,
    viewModel: SwapViewModel = koinViewModel{ parametersOf(swapId) },
) {
    val viewState by viewModel.viewStates().collectAsState()

    SwapView(viewState) {
        viewModel.obtainEvent(it)
    }
}