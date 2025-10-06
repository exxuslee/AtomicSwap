package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exxlexxlee.atomicswap.core.common.navigation.TabAnimation
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.ConnectionStateView
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.ActiveChronicleScreen
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.complete.ConfirmedChronicleScreen
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.models.ViewState
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake.MyMakeChronicleScreen
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.refunded.RefundedChronicleScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChronicleView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {

    ConnectionStateView(
        networkConnect = viewState.connectionState,
        onRetry = {},
    ) {
        AnimatedContent(
            targetState = viewState.selectedTab,
            transitionSpec = {
                TabAnimation.enterTransition(initialState.pos, targetState.pos) togetherWith
                        TabAnimation.exitTransition(initialState.pos, targetState.pos)
            },
        ) { tab ->
            when (tab) {
                FilterStateChronicle.MyMake -> MyMakeChronicleScreen()
                FilterStateChronicle.Active -> ActiveChronicleScreen()
                FilterStateChronicle.Complete -> ConfirmedChronicleScreen()
                FilterStateChronicle.Refund -> RefundedChronicleScreen()
            }
        }
    }

}

@Preview
@Composable
fun ChronicleView_Preview() {
    AppTheme {
        ChronicleView(
            viewState = ViewState(),
            eventHandler = {},
        )
    }
}