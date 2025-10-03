package com.exxlexxlee.atomicswap.feature.tabs.chronicle.active

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.active.models.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    val scrollState = rememberScrollState()
    val navController = LocalNavController.current

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(12.dp))
        Text(viewState.asd)
        Spacer(Modifier.height(24.dp))
    }
}


@Preview
@Composable
fun ActiveView_Preview() {
    AppTheme {
        ActiveView(
            viewState = ViewState(),
            eventHandler = {},
        )
    }
}