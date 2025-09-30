package com.exxlexxlee.atomicswap.feature.root

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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.exxlexxlee.atomicswap.core.common.navigation.AnimationType
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.navigation.LocalPaddingController
import com.exxlexxlee.atomicswap.core.common.navigation.animatedComposable
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.history.HistoryScreen
import com.exxlexxlee.atomicswap.feature.maker.MakerScreen
import com.exxlexxlee.atomicswap.feature.navigation.RoutesMain
import com.exxlexxlee.atomicswap.feature.navigation.isParentSelected
import com.exxlexxlee.atomicswap.feature.settings.about.AboutScreen
import com.exxlexxlee.atomicswap.feature.settings.donate.DonateScreen
import com.exxlexxlee.atomicswap.feature.settings.language.LanguageScreen
import com.exxlexxlee.atomicswap.feature.settings.main.SettingsScreen
import com.exxlexxlee.atomicswap.feature.settings.notification.NotificationScreen
import com.exxlexxlee.atomicswap.feature.settings.terms.TermsScreen
import com.exxlexxlee.atomicswap.feature.taker.TakerScreen
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val settingsUseCase = koinInject<SettingsUseCase>()
    val initialRoute = remember { settingsUseCase.selectedRoute() }
    val bottomDestinations = remember {
        listOf(RoutesMain.Maker, RoutesMain.Taker, RoutesMain.History, RoutesMain.Settings.Main)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                bottomDestinations.forEach { dest ->
                    val currentRoute = backStackEntry?.destination?.route
                    val title = when (dest) {
                        is RoutesMain.Maker -> stringResource(R.string.title_maker)
                        is RoutesMain.Taker -> stringResource(R.string.title_taker)
                        is RoutesMain.History -> stringResource(R.string.title_history)
                        is RoutesMain.Settings -> stringResource(R.string.title_settings)
                    }
                    NavigationBarItem(
                        selected = dest.isParentSelected(currentRoute),
                        onClick = {
                            if (dest.isParentSelected(currentRoute)) {
                                navController.popBackStack(dest.route, inclusive = false)
                            } else {
                                settingsUseCase.selectedRoute(dest.route)
                                navController.navigate(dest.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = when (dest) {
                                    is RoutesMain.Maker -> Icons.Filled.Email
                                    is RoutesMain.Taker -> Icons.Filled.Create
                                    is RoutesMain.History -> Icons.Filled.DateRange
                                    is RoutesMain.Settings -> Icons.Filled.Settings
                                },
                                contentDescription = title
                            )
                        },
                        label = {
                            Text(title)
                        }
                    )
                }
            }
        }
    ) { padding ->
        CompositionLocalProvider(
            LocalNavController provides navController,
            LocalPaddingController provides padding,
        ) {
            NavHost(
                navController = navController,
                startDestination = initialRoute,
                modifier = Modifier.padding(padding)
            ) {
                animatedComposable(RoutesMain.Maker.route) { MakerScreen() }
                animatedComposable(RoutesMain.Taker.route) { TakerScreen() }
                animatedComposable(RoutesMain.History.route) { HistoryScreen() }
                animatedComposable(RoutesMain.Settings.Main.route) { SettingsScreen() }
                animatedComposable(
                    RoutesMain.Settings.Therms.route,
                    animationType = AnimationType.FADE
                ) { TermsScreen() }
                animatedComposable(
                    RoutesMain.Settings.Language.route,
                    animationType = AnimationType.FADE
                ) { LanguageScreen() }
                animatedComposable(
                    RoutesMain.Settings.Notification.route,
                    animationType = AnimationType.FADE
                ) { NotificationScreen() }
                animatedComposable(
                    RoutesMain.Settings.About.route,
                    animationType = AnimationType.FADE
                ) { AboutScreen() }
                animatedComposable(
                    RoutesMain.Settings.Donate.route,
                    animationType = AnimationType.FADE
                ) { DonateScreen() }

            }

        }
    }
}

