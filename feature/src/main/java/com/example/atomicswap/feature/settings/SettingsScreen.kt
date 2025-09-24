package com.example.atomicswap.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.atomicswap.feature.navigation.Routes
import com.example.atomicswap.feature.settings.models.SettingsAction
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = koinViewModel(),
) {
    val viewState by settingsViewModel.viewStates().collectAsState()
    val viewAction by settingsViewModel.viewActions().collectAsState(null)

    SettingsView(viewState) {
        settingsViewModel.obtainEvent(it)
    }


    when (viewAction) {
        SettingsAction.OpenMainScreen -> {
            settingsViewModel.clearAction()
            navController.navigate(Routes.Maker.route)
        }

        null -> {}
    }
}