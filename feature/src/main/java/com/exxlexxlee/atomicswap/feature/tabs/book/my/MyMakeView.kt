package com.exxlexxlee.atomicswap.feature.tabs.book.my

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.feature.tabs.book.my.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.my.models.ViewState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMakeView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {

}


@Preview
@Composable
fun MakeView_Preview() {
    AppTheme {
        MyMakeView(
            viewState = ViewState(),
            eventHandler = {},
        )
    }
}


