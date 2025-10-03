package com.exxlexxlee.atomicswap.feature.root

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.exxlexxlee.atomicswap.core.common.navigation.AnimationType
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.navigation.LocalPaddingController
import com.exxlexxlee.atomicswap.core.common.navigation.animatedComposable
import com.exxlexxlee.atomicswap.core.common.ui.AnimatedFAB
import com.exxlexxlee.atomicswap.core.common.ui.BadgeType
import com.exxlexxlee.atomicswap.core.common.ui.BadgedIcon
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.feature.chronicle.main.ChronicleScreen
import com.exxlexxlee.atomicswap.feature.chronicle.swap.SwapScreen
import com.exxlexxlee.atomicswap.feature.maker.MakerScreen
import com.exxlexxlee.atomicswap.feature.navigation.Routes
import com.exxlexxlee.atomicswap.feature.navigation.isParentSelected
import com.exxlexxlee.atomicswap.feature.root.models.Event
import com.exxlexxlee.atomicswap.feature.settings.about.AboutScreen
import com.exxlexxlee.atomicswap.feature.settings.aggregator.AggregatorScreen
import com.exxlexxlee.atomicswap.feature.settings.donate.DonateScreen
import com.exxlexxlee.atomicswap.feature.settings.language.LanguageScreen
import com.exxlexxlee.atomicswap.feature.settings.main.SettingsScreen
import com.exxlexxlee.atomicswap.feature.settings.notification.NotificationScreen
import com.exxlexxlee.atomicswap.feature.settings.terms.TermsScreen
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
                    viewState.makerRoute,
                    viewState.chronicleRoute,
                    viewState.settingsRoute,
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
                                    contentDescription = title,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        },
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedFAB(backStackEntry?.destination?.route == Routes.MakerRoute().route ||
                    (backStackEntry?.destination?.route == Routes.ChronicleRoute.MainRoute().route &&
                    viewState.selectedChronicleTab == FilterStateChronicle.MAKE)
            ) {

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
                animatedComposable(Routes.MakerRoute().route) { MakerScreen() }

                animatedComposable(Routes.ChronicleRoute.MainRoute().route) { ChronicleScreen() }
                animatedComposable(
                    route = Routes.ChronicleRoute.SwapRoute.route,
                    arguments = listOf(
                        navArgument("swapId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val swapId = backStackEntry.arguments?.getString("swapId") ?: ""
                    SwapScreen(swapId)
                }

                animatedComposable(Routes.SettingsRoute.MainRoute().route) { SettingsScreen() }
                animatedComposable(
                    Routes.SettingsRoute.ThermsRoute.route,
                    animationType = AnimationType.FADE
                ) { TermsScreen() }
                animatedComposable(
                    Routes.SettingsRoute.LanguageRoute.route,
                    animationType = AnimationType.FADE
                ) { LanguageScreen() }
                animatedComposable(
                    Routes.SettingsRoute.NotificationRoute.route,
                    animationType = AnimationType.FADE
                ) { NotificationScreen() }
                animatedComposable(
                    Routes.SettingsRoute.AboutRoute.route,
                    animationType = AnimationType.FADE
                ) { AboutScreen() }
                animatedComposable(
                    Routes.SettingsRoute.DonateRoute.route,
                    animationType = AnimationType.FADE
                ) { DonateScreen() }
                animatedComposable(
                    Routes.SettingsRoute.PriceAggregatorRoute.route,
                    animationType = AnimationType.FADE
                ) { AggregatorScreen() }

            }

        }
    }
}

