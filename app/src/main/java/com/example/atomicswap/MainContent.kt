package com.example.atomicswap

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.atomicswap.core.ui.navigation.animatedComposable
import com.example.atomicswap.feature.history.HistoryScreen
import com.example.atomicswap.feature.maker.MakerScreen
import com.example.atomicswap.feature.settings.SettingsScreen
import com.example.atomicswap.feature.taker.TakerScreen
import com.example.atomicswap.navigation.Routes

@Composable
fun MainContent() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    var selected by remember { mutableStateOf<Routes>(Routes.Maker) }
    val bottomDestinations = remember {
        listOf(Routes.Maker, Routes.Taker, Routes.History, Routes.Settings)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                bottomDestinations.forEach { dest ->
                    NavigationBarItem(
                        selected = selected.route == dest.route,
                        onClick = {
                            selected = dest
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = when (dest) {
                                    is Routes.Maker -> Icons.Filled.Email
                                    is Routes.Taker -> Icons.Filled.Create
                                    is Routes.History -> Icons.Filled.DateRange
                                    is Routes.Settings -> Icons.Filled.Settings
                                },
                                contentDescription = when (dest) {
                                    is Routes.Maker -> stringResource(R.string.bottom_maker)
                                    is Routes.Taker -> stringResource(R.string.bottom_taker)
                                    is Routes.History -> stringResource(R.string.bottom_history)
                                    is Routes.Settings -> stringResource(R.string.bottom_settings)
                                }
                            )
                        },
                        label = {
                            Text(
                                when (dest) {
                                    is Routes.Maker -> stringResource(R.string.bottom_maker)
                                    is Routes.Taker -> stringResource(R.string.bottom_taker)
                                    is Routes.History -> stringResource(R.string.bottom_history)
                                    is Routes.Settings -> stringResource(R.string.bottom_settings)
                                }
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController,
            startDestination = Routes.Maker.route,
            modifier = Modifier.padding(padding)
        ) {
            animatedComposable(Routes.Maker.route) { MakerScreen() }
            animatedComposable(Routes.Taker.route) { TakerScreen() }
            animatedComposable(Routes.History.route) { HistoryScreen() }
            animatedComposable(Routes.Settings.route) { SettingsScreen() }
        }

    }
}
