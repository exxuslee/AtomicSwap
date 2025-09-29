package com.example.atomicswap.feature.settings.about

import androidx.compose.runtime.Composable
import com.example.atomicswap.core.common.navigation.LocalNavController

@Composable
fun AboutScreen() {
    val navController = LocalNavController.current
    AboutView(
        onBack = { navController.popBackStack() }
    )
}
