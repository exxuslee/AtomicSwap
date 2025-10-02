package com.exxlexxlee.atomicswap.feature.settings.notification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.feature.settings.notification.models.Action
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