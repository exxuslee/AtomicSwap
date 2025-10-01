package com.exxlexxlee.atomicswap.data.model.gecko

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeckoPlatform(
    @SerialName("chain_identifier") val chainIdentifier: Int,
    val name: String,
    val shortname: String,
    @SerialName("native_coin_id") val nativeCoinId: String,
    val image: GeckoIcon,
)