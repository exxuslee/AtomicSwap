package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxlexxlee.atomicswap.feature.tabs.common.swap.SwapView
import com.exxlexxlee.atomicswap.feature.tabs.common.swap.SwapViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NewMakeScreen(
    makeId: String,
    viewModel: SwapViewModel = koinViewModel{ parametersOf(makeId) },
) {
    val viewState by viewModel.viewStates().collectAsState()

    SwapView(viewState) {
        viewModel.obtainEvent(it)
    }
}