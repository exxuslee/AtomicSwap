package com.exxlexxlee.atomicswap.feature.tabs.book.subscribe

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.feature.tabs.book.subscribe.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.subscribe.models.ViewState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscribeView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {

}


@Preview
@Composable
fun SubscribeView_Preview() {
    AppTheme {
        SubscribeView(
            viewState = ViewState(),
            eventHandler = {},
        )
    }
}


