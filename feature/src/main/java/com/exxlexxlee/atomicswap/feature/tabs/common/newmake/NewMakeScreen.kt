package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NewMakeScreen(
    makeId: String,
    viewModel: NewMakeViewModel = koinViewModel{ parametersOf(makeId) },
) {
    val viewState by viewModel.viewStates().collectAsState()

    NewMakeView(viewState) {
        viewModel.obtainEvent(it)
    }
}