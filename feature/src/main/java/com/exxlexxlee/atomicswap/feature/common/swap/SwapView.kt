package com.exxlexxlee.atomicswap.feature.common.swap

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.TopAppBar
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.common.swap.models.Event
import com.exxlexxlee.atomicswap.feature.common.swap.models.ViewState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwapView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    val scrollState = rememberScrollState()
    val navController = LocalNavController.current

    Column {
        TopAppBar(stringResource(R.string.language)) {
            navController.popBackStack()
        }
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(12.dp))
            Text(viewState.swapId)
            Spacer(Modifier.height(24.dp))
        }
    }

}

@Preview
@Composable
fun AggregatorView_Preview() {
    AppTheme {
        SwapView(
            viewState = ViewState("swapId"),
            eventHandler = { }
        )
    }
}