package com.example.atomicswap

import androidx.compose.foundation.layout.padding
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
import com.example.atomicswap.ui.bottomDestinations
import com.example.atomicswap.ui.iconFor
import com.example.atomicswap.ui.labelFor


@Composable
fun MainContent() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    var selected by remember { mutableStateOf<Routes>(Routes.Taker) }

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
                                imageVector = iconFor(dest),
                                contentDescription = labelFor(dest)
                            )
                        },
                        label = { Text(labelFor(dest)) }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController,
            startDestination = Routes.Taker.route,
            modifier = Modifier.padding(padding)
        ) {
            animatedComposable(Routes.Taker.route) { TakerScreen() }
            animatedComposable(Routes.Maker.route) { MakerScreen() }
            animatedComposable(Routes.History.route) { HistoryScreen() }
            animatedComposable(Routes.Settings.route,) { SettingsScreen() }
        }

    }
}
