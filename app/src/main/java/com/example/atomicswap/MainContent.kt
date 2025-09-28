package com.example.atomicswap

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.atomicswap.core.common.navigation.AnimationType
import com.example.atomicswap.core.common.navigation.animatedComposable
import com.example.atomicswap.domain.repository.SettingsRepository
import com.example.atomicswap.domain.usecases.SettingsUseCase
import com.example.atomicswap.feature.history.HistoryScreen
import com.example.atomicswap.feature.maker.MakerScreen
import com.example.atomicswap.feature.navigation.Routes
import com.example.atomicswap.feature.settings.about.AboutScreen
import com.example.atomicswap.feature.settings.donate.DonateScreen
import com.example.atomicswap.feature.settings.language.LanguageScreen
import com.example.atomicswap.feature.settings.main.SettingsScreen
import com.example.atomicswap.feature.settings.notification.NotificationScreen
import com.example.atomicswap.feature.settings.terms.TermsScreen
import com.example.atomicswap.feature.taker.TakerScreen
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val settingsUseCase = koinInject<SettingsUseCase>()
    val initialRoute = remember { settingsUseCase.selectedRoute() }
    var selected by remember { mutableStateOf(Routes.routeToRoutes(initialRoute)) }
    val bottomDestinations = remember {
        listOf(Routes.Maker, Routes.Taker, Routes.History, Routes.Settings.Main)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                bottomDestinations.forEach { dest ->
                    NavigationBarItem(
                        selected = selected.route == dest.route,
                        onClick = {
                            selected = dest
                            settingsUseCase.selectedRoute(dest.route)
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    when (dest) {
                                        is Routes.Settings -> {
                                            inclusive = false
                                            saveState = true
                                        }

                                        else -> {
                                            saveState = true
                                        }
                                    }
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
            startDestination = initialRoute,
            modifier = Modifier.padding(padding)
        ) {
            animatedComposable(
                Routes.Maker.route,
            ) { MakerScreen(navController) }
            animatedComposable(
                Routes.Taker.route,
            ) { TakerScreen(navController) }
            animatedComposable(
                Routes.History.route,
            ) { HistoryScreen(navController) }
            animatedComposable(
                Routes.Settings.Main.route,
            ) { SettingsScreen(navController) }
            animatedComposable(
                Routes.Settings.Therms.route,
                animationType = AnimationType.FADE,
            ) { TermsScreen(navController) }
            animatedComposable(
                Routes.Settings.Language.route,
                animationType = AnimationType.FADE,
            ) { LanguageScreen(navController) }
            animatedComposable(
                Routes.Settings.Notification.route,
                animationType = AnimationType.FADE,
            ) { NotificationScreen(navController) }
            animatedComposable(
                Routes.Settings.About.route,
                animationType = AnimationType.FADE,
            ) { AboutScreen(navController) }
            animatedComposable(
                Routes.Settings.Donate.route,
                animationType = AnimationType.FADE,
            ) { DonateScreen(navController) }

        }

    }
}
