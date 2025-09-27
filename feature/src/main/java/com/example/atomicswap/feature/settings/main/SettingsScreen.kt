package com.example.atomicswap.feature.settings.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.atomicswap.feature.navigation.Routes
import com.example.atomicswap.feature.settings.main.models.Action
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
        Action.OpenTermsScreen -> {
            viewModel.clearAction()
            navController.navigate(Routes.Settings.Therms.route)
        }

        Action.OpenLanguageScreen -> {
            viewModel.clearAction()
            navController.navigate(Routes.Settings.Language.route)
        }

        Action.OpenNotificationScreen -> {
            viewModel.clearAction()
            navController.navigate(Routes.Settings.Notification.route)
        }

        null -> {}
    }
}