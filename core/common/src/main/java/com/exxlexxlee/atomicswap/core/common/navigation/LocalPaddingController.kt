package com.exxlexxlee.atomicswap.core.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

val LocalPaddingController = staticCompositionLocalOf<PaddingValues> {
    error("NavController not provided")
}