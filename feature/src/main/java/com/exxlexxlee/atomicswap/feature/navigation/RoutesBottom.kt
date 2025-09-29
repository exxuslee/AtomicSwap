package com.exxlexxlee.atomicswap.feature.navigation

sealed class RoutesBottom(val route: String) {
    data object ConnectWc : RoutesBottom("connect")
}
