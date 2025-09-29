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
import com.exxlexxlee.atomicswap.core.common.navigation.animatedComposable
import com.exxlexxlee.atomicswap.domain.usecases.SettingsUseCase
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.history.HistoryScreen
import com.exxlexxlee.atomicswap.feature.maker.MakerScreen
import com.exxlexxlee.atomicswap.feature.navigation.Routes
import com.exxlexxlee.atomicswap.feature.navigation.isParentSelected
import com.exxlexxlee.atomicswap.feature.settings.about.AboutScreen
import com.exxlexxlee.atomicswap.feature.settings.donate.DonateScreen
import com.exxlexxlee.atomicswap.feature.settings.language.LanguageScreen
import com.exxlexxlee.atomicswap.feature.settings.main.SettingsScreen
import com.exxlexxlee.atomicswap.feature.settings.notification.NotificationScreen
import com.exxlexxlee.atomicswap.feature.settings.terms.TermsScreen
import com.exxlexxlee.atomicswap.feature.taker.TakerScreen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.reown.appkit.ui.appKitGraph
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun MainContent() {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    val backStackEntry by navController.currentBackStackEntryAsState()
    val settingsUseCase = koinInject<SettingsUseCase>()
    val initialRoute = remember { settingsUseCase.selectedRoute() }
    val bottomDestinations = remember {
        listOf(Routes.Maker, Routes.Taker, Routes.History, Routes.Settings.Main)
    }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    bottomDestinations.forEach { dest ->
                        val currentRoute = backStackEntry?.destination?.route
                        val title = when (dest) {
                            is Routes.Maker -> stringResource(R.string.title_maker)
                            is Routes.Taker -> stringResource(R.string.title_taker)
                            is Routes.History -> stringResource(R.string.title_history)
                            is Routes.Settings -> stringResource(R.string.title_settings)
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
                                        is Routes.Maker -> Icons.Filled.Email
                                        is Routes.Taker -> Icons.Filled.Create
                                        is Routes.History -> Icons.Filled.DateRange
                                        is Routes.Settings -> Icons.Filled.Settings
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
                LocalNavController provides navController
            ) {
                NavHost(
                    navController = navController,
                    startDestination = initialRoute,
                    modifier = Modifier.padding(padding)
                ) {
                    animatedComposable(Routes.Maker.route) { MakerScreen() }
                    animatedComposable(Routes.Taker.route) { TakerScreen() }
                    animatedComposable(Routes.History.route) { HistoryScreen() }
                    animatedComposable(Routes.Settings.Main.route) { SettingsScreen() }
                    animatedComposable(
                        Routes.Settings.Therms.route,
                        animationType = AnimationType.FADE
                    ) { TermsScreen() }
                    animatedComposable(
                        Routes.Settings.Language.route,
                        animationType = AnimationType.FADE
                    ) { LanguageScreen() }
                    animatedComposable(
                        Routes.Settings.Notification.route,
                        animationType = AnimationType.FADE
                    ) { NotificationScreen() }
                    animatedComposable(
                        Routes.Settings.About.route,
                        animationType = AnimationType.FADE
                    ) { AboutScreen() }
                    animatedComposable(
                        Routes.Settings.Donate.route,
                        animationType = AnimationType.FADE
                    ) { DonateScreen() }
                    appKitGraph(navController)
                }
            }
        }
    }
}
