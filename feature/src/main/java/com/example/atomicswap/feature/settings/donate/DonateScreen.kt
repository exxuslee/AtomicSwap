package com.example.atomicswap.feature.settings.donate

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DonateScreen(
    navController: NavController,
) {
    DonateView(
        onBack = { navController.popBackStack() }
    )
}
