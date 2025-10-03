package com.exxlexxlee.atomicswap.feature.tabs.settings.about

import androidx.compose.runtime.Composable
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController

@Composable
fun AboutScreen() {
    val navController = LocalNavController.current
    AboutView(
        onBack = { navController.popBackStack() }
    )
}
