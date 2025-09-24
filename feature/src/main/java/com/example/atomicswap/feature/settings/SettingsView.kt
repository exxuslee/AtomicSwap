package com.example.atomicswap.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.models.SettingsAction
import com.example.atomicswap.feature.settings.models.SettingsEvent
import com.example.atomicswap.feature.settings.models.SettingsViewState

@Composable
fun SettingsView(viewState: SettingsViewState, eventHandler: (SettingsEvent) -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.title_settings))
        Switch(
            checked = viewState.isDark,
            onCheckedChange = { eventHandler.invoke(SettingsEvent.IsDark(it)) },
            colors = SwitchDefaults.colors()
        )

        Button(
            onClick = {
                eventHandler.invoke(SettingsEvent.MainAction)
            }
        ) { Text("Go to Main") }
    }
}

@PreviewLightDark
@Composable
fun ProfileView_Preview() {
    AppTheme {
        SettingsView(
            viewState = SettingsViewState(),
            eventHandler = { }
        )
    }
}