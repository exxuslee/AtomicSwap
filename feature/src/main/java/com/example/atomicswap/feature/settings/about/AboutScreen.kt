package com.example.atomicswap.feature.settings.about

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AboutScreen(
    navController: NavController,
) {
    AboutView(
        onBack = { navController.popBackStack() }
    )
}
