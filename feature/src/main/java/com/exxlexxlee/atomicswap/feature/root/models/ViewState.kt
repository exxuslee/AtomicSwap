package com.exxlexxlee.atomicswap.feature.root.models

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.feature.navigation.Routes

data class ViewState(
    val initialRoute: String,
    val maker: Routes.Maker,
    val chronicle: Routes.Chronicle,
    val settings: Routes.Settings,
    val selectedChronicleTab: FilterStateChronicle,
)