package com.example.atomicswap.feature.settings.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.atomicswap.feature.navigation.Routes
import com.example.atomicswap.feature.settings.main.models.SettingsAction
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    SettingsView(viewState) {
        viewModel.obtainEvent(it)
    }


    when (viewAction) {
        SettingsAction.OpenTermsScreen -> {
            viewModel.clearAction()
            navController.navigate(Routes.Settings.Therms.route)
        }

        null -> {}
    }
}