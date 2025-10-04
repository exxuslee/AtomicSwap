package com.exxlexxlee.atomicswap.core.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.exxlexxlee.atomicswap.core.network.ConnectionManager.ConnectionState


@Composable
fun ConnectionStateView(
    networkConnect: ConnectionState,
    onRetry: () -> Unit,
    content: @Composable () -> Unit,
) {
    Column {
        when (networkConnect) {
            is ConnectionState.Connected -> content()
            ConnectionState.ConnectedNoInternet -> ListErrorView(
                "Connected, no internet",
                onRetry = onRetry
            )

            ConnectionState.Disconnected -> ListErrorViewAnimated(
                "Disconnected",
                onRetry = onRetry,
            )
        }
    }
}
