package com.example.atomicswap.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.atomicswap.R
import com.example.atomicswap.navigation.Routes


val bottomDestinations = listOf(Routes.Taker, Routes.Maker, Routes.History, Routes.Settings)

fun iconFor(dest: Routes): ImageVector = when (dest) {
    is Routes.Taker -> Icons.Filled.Create
    is Routes.Maker -> Icons.Filled.Email
    is Routes.History -> Icons.Filled.DateRange
    is Routes.Settings -> Icons.Filled.Settings
}

@Composable
fun labelFor(dest: Routes): String = when (dest) {
    is Routes.Taker -> stringResource(R.string.bottom_taker)
    is Routes.Maker -> stringResource(R.string.bottom_maker)
    is Routes.History -> stringResource(R.string.bottom_history)
    is Routes.Settings -> stringResource(R.string.bottom_settings)
}
