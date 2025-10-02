package com.exxlexxlee.atomicswap.feature.chronicle.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.Event
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.ViewState


@Composable
fun ChronicleView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {

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