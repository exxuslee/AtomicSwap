package com.example.atomicswap.feature.settings.donate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.atomicswap.feature.settings.donate.models.Action
import org.koin.androidx.compose.koinViewModel

@Composable
fun DonateScreen(
    navController: NavController,
    viewModel: DonateViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    DonateView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {
        is Action.PopBackStack -> {
            viewModel.clearAction()
            navController.popBackStack()
        }
        null -> {}
    }
}
