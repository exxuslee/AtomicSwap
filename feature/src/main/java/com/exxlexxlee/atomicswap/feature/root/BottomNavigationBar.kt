package com.exxlexxlee.atomicswap.feature.root

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.exxlexxlee.atomicswap.core.common.ui.BadgeType
import com.exxlexxlee.atomicswap.core.common.ui.BadgedIcon
import com.exxlexxlee.atomicswap.feature.navigation.isParentSelected
import com.exxlexxlee.atomicswap.feature.root.models.Event
import com.exxlexxlee.atomicswap.feature.root.models.ViewState

@Composable
fun BottomNavigationBar(
    viewState: ViewState,
    backStackEntry: NavBackStackEntry?,
    navController: NavHostController,
    eventHandler: (Event) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
    ) {
        listOf(
            viewState.bookRoute,
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
                        eventHandler.invoke(Event.MainRoute(dest.route))
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
                        val icon =
                            if (dest.isParentSelected(currentRoute)) dest.iconSelect() else dest.icon()
                        Icon(
                            painter = icon,
                            contentDescription = title,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                alwaysShowLabel = false,
            )
        }
    }
}