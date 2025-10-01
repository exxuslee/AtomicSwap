package com.exxlexxlee.atomicswap.data.model.gecko

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenDetail(
    override val id: String,
    override val symbol: String,
    override val name: String,
    override val platforms: Map<String, String>,

    @SerialName("web_slug")
    val webSlug: String,

    @SerialName("asset_platform_id")
    val assetPlatformId: String? = null,

    @SerialName("detail_platforms")
    val detailPlatforms: Map<String, DetailPlatform> = emptyMap(),

    @SerialName("block_time_in_minutes")
    val blockTimeInMinutes: Int? = null,

    @SerialName("hashing_algorithm")
    val hashingAlgorithm: String? = null,

    val categories: List<String> = emptyList(),

    @SerialName("preview_listing")
    val previewListing: Boolean = false,

    @SerialName("public_notice")
    val publicNotice: String? = null,

    @SerialName("additional_notices")
    val additionalNotices: List<String> = emptyList(),

    val description: Map<String, String> = emptyMap(),

    val image: GeckoIcon,

    @SerialName("country_origin")
    val countryOrigin: String? = null,

    @SerialName("genesis_date")
    val genesisDate: String? = null,

    @SerialName("contract_address")
    val contractAddress: String? = null,

    @SerialName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double? = null,

    @SerialName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double? = null,

    @SerialName("watchlist_portfolio_users")
    val watchlistPortfolioUsers: Int? = null,

    @SerialName("market_cap_rank")
    val marketCapRank: Int? = null,

    @SerialName("status_updates")
    val statusUpdates: List<String> = emptyList(),

    @SerialName("last_updated")
    val lastUpdated: String? = null
): GeckoToken(id, symbol,  name, platforms)

@Serializable
data class DetailPlatform(
    @SerialName("decimal_place")
    val decimalPlace: Int? = null,

    @SerialName("contract_address")
    val contractAddress: String? = null,

    @SerialName("geckoterminal_url")
    val geckoterminalUrl: String? = null
)
