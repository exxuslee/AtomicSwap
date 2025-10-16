package com.exxlexxlee.atomicswap.feature.tabs.book.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exxlexxlee.atomicswap.core.common.navigation.TabAnimation
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.ConnectionStateView
import com.exxlexxlee.atomicswap.domain.model.FilterStateBook
import com.exxlexxlee.atomicswap.feature.tabs.book.main.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.main.models.ViewState
import com.exxlexxlee.atomicswap.feature.tabs.book.makes.MakesBookScreen
import com.exxlexxlee.atomicswap.feature.tabs.book.my.MyMakeBookScreen
import com.exxlexxlee.atomicswap.feature.tabs.book.subscribe.SubscribeBookScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookView(
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
                FilterStateBook.Make -> MakesBookScreen()
                FilterStateBook.MyMake -> MyMakeBookScreen()
                FilterStateBook.Subscription -> SubscribeBookScreen()
            }
        }
    }
}


@Preview
@Composable
fun BookView_Preview() {
    AppTheme {
        BookView(
            viewState = ViewState(),
            eventHandler = {},
        )
    }
}