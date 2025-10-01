package com.exxlexxlee.atomicswap.data.model.gecko

import kotlinx.serialization.Serializable

@Serializable
data class GeckoIcon(
    val thumb: String,
    val small: String,
    val large: String
)