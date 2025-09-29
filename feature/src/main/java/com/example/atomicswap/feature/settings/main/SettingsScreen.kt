package com.example.atomicswap.feature.settings.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.atomicswap.core.common.navigation.LocalNavController
import com.example.atomicswap.feature.navigation.Routes
import com.example.atomicswap.feature.settings.main.models.Action
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val navController = LocalNavController.current

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

        Action.OpenAboutScreen -> {
            viewModel.clearAction()
            navController.navigate(Routes.Settings.About.route)
        }

        Action.OpenDonateScreen -> {
            viewModel.clearAction()
            navController.navigate(Routes.Settings.Donate.route)
        }

        null -> {}
    }
}