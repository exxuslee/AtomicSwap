package com.exxlexxlee.atomicswap.feature.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.exxlexxlee.atomicswap.core.common.navigation.AnimationType
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.navigation.LocalPaddingController
import com.exxlexxlee.atomicswap.core.common.navigation.animatedComposable
import com.exxlexxlee.atomicswap.core.common.ui.AnimatedFAB
import com.exxlexxlee.atomicswap.core.common.ui.BadgeType
import com.exxlexxlee.atomicswap.core.common.ui.BadgedIcon
import com.exxlexxlee.atomicswap.feature.chronicle.main.HistoryScreen
import com.exxlexxlee.atomicswap.feature.maker.MakerScreen
import com.exxlexxlee.atomicswap.feature.navigation.RoutesMain
import com.exxlexxlee.atomicswap.feature.navigation.isParentSelected
import com.exxlexxlee.atomicswap.feature.root.models.Event
import com.exxlexxlee.atomicswap.feature.settings.about.AboutScreen
import com.exxlexxlee.atomicswap.feature.settings.aggregator.AggregatorScreen
import com.exxlexxlee.atomicswap.feature.settings.donate.DonateScreen
import com.exxlexxlee.atomicswap.feature.settings.language.LanguageScreen
import com.exxlexxlee.atomicswap.feature.settings.main.SettingsScreen
import com.exxlexxlee.atomicswap.feature.settings.notification.NotificationScreen
import com.exxlexxlee.atomicswap.feature.settings.terms.TermsScreen
import com.exxlexxlee.atomicswap.feature.taker.TakerScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    viewModel: MainViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                listOf(
                    viewState.maker,
                    viewState.taker,
                    viewState.history,
                    viewState.settings,
                ).forEach { dest ->
                    val currentRoute = backStackEntry?.destination?.route
                    val title = stringResource(dest.label)
                    NavigationBarItem(
                        selected = dest.isParentSelected(currentRoute),
                        onClick = {
                            if (dest.isParentSelected(currentRoute)) {
                                navController.popBackStack(dest.route, inclusive = false)
                            } else {
                                viewModel.obtainEvent(Event.SelectedRoute(dest.route))
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
                            BadgedIcon(BadgeType.fromInt(dest.badge)) {
                                Icon(
                                    painterResource(dest.icon),
                                    contentDescription = title
                                )
                            }
                        },
                        label = { Text(title) }
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedFAB(backStackEntry?.destination?.route == RoutesMain.Maker().route) {

            }
        }
    ) { padding ->
        CompositionLocalProvider(
            LocalNavController provides navController,
            LocalPaddingController provides padding,
        ) {
            NavHost(
                navController = navController,
                startDestination = viewState.initialRoute,
                modifier = Modifier.padding(padding)
            ) {
                animatedComposable(RoutesMain.Maker().route) { MakerScreen() }
                animatedComposable(RoutesMain.Taker().route) { TakerScreen() }
                animatedComposable(RoutesMain.Chronicle.Main().route) { HistoryScreen() }
                animatedComposable(RoutesMain.Settings.Main().route) { SettingsScreen() }
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
                animatedComposable(
                    RoutesMain.Settings.PriceAggregator.route,
                    animationType = AnimationType.FADE
                ) { AggregatorScreen() }

            }

        }
    }
}

