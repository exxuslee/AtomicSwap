package com.example.atomicswap.feature.settings.terms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.atomicswap.feature.navigation.Routes
import com.example.atomicswap.feature.settings.main.SettingsView
import com.example.atomicswap.feature.settings.main.SettingsViewModel
import com.example.atomicswap.feature.settings.main.models.SettingsAction
import org.koin.androidx.compose.koinViewModel

@Composable
fun TermsScreen(
    viewModel: TermsViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    TermsView(viewState) {
        viewModel.obtainEvent(it)
    }


    when (viewAction) {
        null -> {}
    }
}