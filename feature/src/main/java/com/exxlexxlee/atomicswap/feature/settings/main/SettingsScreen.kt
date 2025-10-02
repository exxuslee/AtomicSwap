package com.exxlexxlee.atomicswap.feature.settings.main

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.walletconnect.ConnectWcBottomSheet
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.navigation.RoutesMain
import com.exxlexxlee.atomicswap.feature.settings.main.models.Action
import com.exxlexxlee.atomicswap.feature.settings.main.models.Event
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    SettingsView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {
        Action.LocaleStorageDialog -> AlertDialog(
            onDismissRequest = { viewModel.clearAction() },
            title = { Text(text = stringResource(id = R.string.clear_storage_title)) },
            text = { Text(text = stringResource(id = R.string.clear_storage_message)) },
            confirmButton = {
                TextButton(onClick = { viewModel.obtainEvent(Event.ConfirmClearStorage) }) {
                    Text(
                        text = stringResource(id = R.string.clear_storage_confirm),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.clearAction() }) {
                    Text(
                        text = stringResource(id = R.string.clear_storage_cancel),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        )

        Action.ConnectWcDialog -> {
            ConnectWcBottomSheet {
                viewModel.clearAction()
            }
        }

        null -> {}
    }
}