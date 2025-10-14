package com.exxlexxlee.atomicswap.feature.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.PrimaryIndicator
import androidx.compose.material3.TabRowDefaults.primaryContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.exxlexxlee.atomicswap.core.common.R
import com.exxlexxlee.atomicswap.core.common.ui.BadgeType
import com.exxlexxlee.atomicswap.core.common.ui.BadgedIcon
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.feature.common.TokenSelector
import com.exxlexxlee.atomicswap.feature.navigation.Routes.ChronicleRoute
import com.exxlexxlee.atomicswap.feature.navigation.Routes.MakerRoute
import com.exxlexxlee.atomicswap.feature.navigation.Routes.SettingsRoute
import com.exxlexxlee.atomicswap.feature.navigation.asRoute
import com.exxlexxlee.atomicswap.feature.navigation.isPrimaryRoute
import com.exxlexxlee.atomicswap.feature.root.models.Event
import com.exxlexxlee.atomicswap.feature.root.models.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    viewState: ViewState,
    backStackEntry: NavBackStackEntry?,
    navController: NavHostController,
    eventHandler: (Event) -> Unit,
) {
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
                                eventHandler.invoke(Event.ChronicleTab(filterState))
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
                RowUniversal(
                    modifier = Modifier.padding(0.dp, topPadding, 0.dp, 0.dp),
                    verticalPadding = 0.dp,
                ) {
                    TokenSelector(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp),
                        token = viewState.filterToken.first,
                        placeholder = stringResource(R.string.from),
                    ) {
                        eventHandler.invoke(Event.TakerTokenSheet)
                    }

                    IconButton(
                        onClick = {
                            eventHandler.invoke(Event.SwitchToken)
                        }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            painter = painterResource(id = com.exxlexxlee.atomicswap.feature.R.drawable.outline_arrow_right_alt_24),
                            contentDescription = "token selector",
                        )
                    }

                    TokenSelector(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp),
                        token = viewState.filterToken.second,
                        placeholder = stringResource(R.string.to),
                    ) {
                        eventHandler.invoke(Event.MakerTokenSheet)
                    }

                }
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
}