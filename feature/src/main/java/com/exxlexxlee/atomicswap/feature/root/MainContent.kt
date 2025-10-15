package com.exxlexxlee.atomicswap.feature.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.feature.common.swap.SwapScreen
import com.exxlexxlee.atomicswap.feature.common.tokens.TokensModalBottomSheet
import com.exxlexxlee.atomicswap.feature.navigation.Routes.BookRoute
import com.exxlexxlee.atomicswap.feature.navigation.Routes.ChronicleRoute
import com.exxlexxlee.atomicswap.feature.navigation.Routes.SettingsRoute
import com.exxlexxlee.atomicswap.feature.root.models.Action
import com.exxlexxlee.atomicswap.feature.root.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.main.BookScreen
import com.exxlexxlee.atomicswap.feature.tabs.chronicle.main.ChronicleScreen
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
            TopNavigationBar(
                viewState = viewState,
                backStackEntry = backStackEntry,
                navController = navController,
            ) { event ->
                viewModel.obtainEvent(event)
            }
        },
        bottomBar = {
            BottomNavigationBar(
                viewState = viewState,
                backStackEntry = backStackEntry,
                navController = navController,
            ) { event ->
                viewModel.obtainEvent(event)
            }
        },
        floatingActionButton = {
            AnimatedFAB(
                backStackEntry?.destination?.route == BookRoute.MakeRoute.route ||
                        backStackEntry?.destination?.route == BookRoute.MyMakeRoute.route ||
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
                animatedComposable(BookRoute.MainRoute().route) { BookScreen() }

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

        when (viewAction) {
            null -> {}
        }
    }
}

