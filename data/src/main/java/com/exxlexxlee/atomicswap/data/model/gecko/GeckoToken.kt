package com.exxlexxlee.atomicswap.data.model.gecko

import kotlinx.serialization.Serializable

@Serializable
open class GeckoToken(
    open val id: String,
    open val symbol: String,
    open val name: String,
    open val platforms: Map<String, String>,
)