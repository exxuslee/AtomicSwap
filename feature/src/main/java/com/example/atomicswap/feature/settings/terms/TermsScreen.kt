package com.example.atomicswap.feature.settings.terms

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.atomicswap.core.common.navigation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsScreen() {
    val navController = LocalNavController.current

    TermsView(
        onBack = { navController.popBackStack()}
    )
}
