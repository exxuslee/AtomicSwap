package com.example.atomicswap.feature.settings.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.notification.models.Event
import com.example.atomicswap.feature.settings.notification.models.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    val scrollState = rememberScrollState()

    Column {
        TopAppBar(
            windowInsets = WindowInsets(0, 0, 0, 0),
            title = {
                Text(
                    text = stringResource(R.string.notifications),
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    eventHandler.invoke(Event.PopBackStack)
                }) {
                    Icon(
                        painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {

        }
    }

}


@Preview
@Composable
fun TermsView_Preview() {
    AppTheme {
        NotificationView(
            viewState = ViewState(),
            eventHandler = { }
        )
    }
}