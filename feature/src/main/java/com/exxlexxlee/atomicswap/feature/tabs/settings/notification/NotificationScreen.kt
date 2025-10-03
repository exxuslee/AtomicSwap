package com.exxlexxlee.atomicswap.feature.tabs.settings.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    LaunchedEffect(Unit) {
        if (viewState.items.isEmpty()) viewModel.sync()
    }

    NotificationView(viewState) {
        viewModel.obtainEvent(it)
    }

}