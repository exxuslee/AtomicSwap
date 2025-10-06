package com.exxlexxlee.atomicswap.feature.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.PrimaryIndicator
import androidx.compose.material3.TabRowDefaults.primaryContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.exxlexxlee.atomicswap.core.common.R
import com.exxlexxlee.atomicswap.core.common.navigation.AnimationType
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.navigation.LocalPaddingController
import com.exxlexxlee.atomicswap.core.common.navigation.animatedComposable
import com.exxlexxlee.atomicswap.core.common.ui.AnimatedFAB
import com.exxlexxlee.atomicswap.core.common.ui.BadgeType
import com.exxlexxlee.atomicswap.core.common.ui.BadgedIcon
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.feature.common.swap.SwapScreen
import com.exxlexxlee.atomicswap.feature.navigation.Routes.ChronicleRoute
import com.exxlexxlee.atomicswap.feature.navigation.Routes.MakerRoute
import com.exxlexxlee.atomicswap.feature.navigation.Routes.SettingsRoute
import com.exxlexxlee.atomicswap.feature.navigation.asRoute
import com.exxlexxlee.atomicswap.feature.navigation.isParentSelected
import com.exxlexxlee.atomicswap.feature.navigation.isPrimaryRoute
import com.exxlexxlee.atomicswap.feature.root.models.Event
import com.exxlexxlee.atomicswap.feature.root.models.Event.SelectChronicleTab
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.ChronicleScreen
import com.exxlexxlee.atomicswap.feature.tabs.maker.MakerScreen
import com.exxlexxlee.atomicswap.feature.tabs.settings.about.AboutScreen
import com.exxlexxlee.atomicswap.feature.tabs.settings.aggregator.AggregatorScreen
import com.exxlexxlee.atomicswap.feature.tabs.settings.donate.DonateScreen
import com.exxlexxlee.atomicswap.feature.tabs.settings.language.LanguageScreen
import com.exxlexxlee.atomicswap.feature.tabs.settings.main.SettingsScreen
import com.exxlexxlee.atomicswap.feature.tabs.settings.notification.NotificationScreen
import com.exxlexxlee.atomicswap.feature.tabs.settings.scanner.ScannerScreen
import com.exxlexxlee.atomicswap.feature.tabs.settings.terms.TermsScreen
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
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            val currentRoute = backStackEntry?.destination?.asRoute()
            if (currentRoute?.isPrimaryRoute() == false) {
                TopAppBar(
                    windowInsets = WindowInsets.statusBars,
                    title = {
                        Text(
                            text = currentRoute.label(),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                    )
                )
            } else {
                val topPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                when (currentRoute) {
                    is ChronicleRoute -> {
                        PrimaryTabRow(
                            modifier = Modifier.padding(top = topPadding),
                            selectedTabIndex = viewState.selectedChronicleTab.pos,
                            indicator = {
                                PrimaryIndicator(
                                    color = primaryContentColor,
                                    modifier = Modifier
                                        .tabIndicatorOffset(
                                            viewState.selectedChronicleTab.pos,
                                            matchContentSize = false
                                        ),
                                    width = Dp.Unspecified,
                                )
                            },
                            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                        ) {
                            FilterStateChronicle.values.forEach { filterState ->
                                Tab(
                                    selected = viewState.selectedChronicleTab == filterState,
                                    onClick = {
                                        viewModel.obtainEvent(
                                            SelectChronicleTab(filterState)
                                        )
                                    },
                                    icon = {
                                        BadgedIcon(
                                            badge = BadgeType.fromInt(
                                                viewState.swapFilterBadgeType[filterState] ?: 0
                                            )
                                        ) {
                                            Icon(
                                                modifier = Modifier.padding(horizontal = 4.dp),
                                                painter = painterResource(filterState.icon),
                                                contentDescription = filterState.pos.toString()
                                            )
                                        }
                                    },
                                )
                            }
                        }
                    }

                    is MakerRoute -> {

                    }

                    is SettingsRoute.MainRoute -> {
                        RowUniversal(
                            modifier = Modifier.padding(top = topPadding, end = 12.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            HsIconButton(
                                onClick = {
                                    navController.navigate(SettingsRoute.NotificationRoute.route)
                                },
                            ) {
                                BadgedIcon(
                                    if (viewState.pushUnreadCount > 0) BadgeType.BadgeNumber(
                                        viewState.pushUnreadCount
                                    )
                                    else null
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .padding(horizontal = 2.dp)
                                            .size(24.dp),
                                        painter = painterResource(id = R.drawable.outline_notifications_24),
                                        contentDescription = "notification center",
                                    )
                                }
                            }
                            HsIconButton(
                                onClick = {
                                    navController.navigate(SettingsRoute.ScannerRoute.route)
                                },
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(horizontal = 2.dp)
                                        .size(24.dp),
                                    painter = painterResource(id = R.drawable.outline_qr_code_scanner_24),
                                    contentDescription = "qr-scanner",
                                )
                            }
                        }
                    }

                    else -> null
                }
            }

        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp,
            ) {
                listOf(
                    viewState.makerRoute,
                    viewState.chronicleRoute,
                    viewState.settingsRoute,
                ).forEach { dest ->
                    val currentRoute = backStackEntry?.destination?.route
                    val title = dest.label()
                    NavigationBarItem(
                        selected = dest.isParentSelected(currentRoute),
                        onClick = {
                            if (dest.isParentSelected(currentRoute)) {
                                navController.popBackStack(dest.route, inclusive = false)
                            } else {
                                viewModel.obtainEvent(Event.SelectMainRoute(dest.route))
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
                        alwaysShowLabel = false,
                    )
                }
            }
        },
        floatingActionButton = {
            AnimatedFAB(
                backStackEntry?.destination?.route == MakerRoute().route ||
                        (backStackEntry?.destination?.route == ChronicleRoute.MainRoute().route &&
                                viewState.selectedChronicleTab == FilterStateChronicle.MyMake)
            ) {

            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) { padding ->
        CompositionLocalProvider(
            LocalNavController provides navController,
            LocalPaddingController provides padding,
        ) {
            NavHost(
                navController = navController,
                startDestination = viewState.initialRoute,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                animatedComposable(MakerRoute().route) { MakerScreen() }

                animatedComposable(ChronicleRoute.MainRoute().route) { ChronicleScreen() }
                animatedComposable(
                    route = ChronicleRoute.SwapRoute.route,
                    arguments = listOf(
                        navArgument("swapId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val swapId = backStackEntry.arguments?.getString("swapId") ?: ""
                    SwapScreen(swapId)
                }

                animatedComposable(SettingsRoute.MainRoute().route) { SettingsScreen() }
                animatedComposable(
                    SettingsRoute.ThermsRoute.route,
                    animationType = AnimationType.FADE
                ) { TermsScreen() }
                animatedComposable(
                    SettingsRoute.LanguageRoute.route,
                    animationType = AnimationType.FADE
                ) { LanguageScreen() }
                animatedComposable(
                    SettingsRoute.NotificationRoute.route,
                    animationType = AnimationType.FADE
                ) { NotificationScreen() }
                animatedComposable(
                    SettingsRoute.AboutRoute.route,
                    animationType = AnimationType.FADE
                ) { AboutScreen() }
                animatedComposable(
                    SettingsRoute.DonateRoute.route,
                    animationType = AnimationType.FADE
                ) { DonateScreen() }
                animatedComposable(
                    SettingsRoute.PriceAggregatorRoute.route,
                    animationType = AnimationType.FADE
                ) { AggregatorScreen() }
                animatedComposable(
                    SettingsRoute.ScannerRoute.route,
                    animationType = AnimationType.FADE
                ) { ScannerScreen() }

            }

        }
    }
}

