package com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.ListEmptyView
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.common.SwapViewItem
import com.exxlexxlee.atomicswap.feature.navigation.Routes
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded.models.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefundedView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    val navController = LocalNavController.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (viewState.swaps.isEmpty()) {
            ListEmptyView(
                text = stringResource(R.string.refund_empty_list),
                icon = R.drawable.outline_empty_dashboard_24
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewState.swaps) { swap ->
                    SwapViewItem(swap = swap) {
                        navController.navigate(Routes.ChronicleRoute.SwapRoute.createRoute(swap.swapId))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RefundedView_Preview() {
    AppTheme {
        RefundedView(
            viewState = ViewState(),
            eventHandler = {},
        )
    }
}


