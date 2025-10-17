package com.exxlexxlee.atomicswap.feature.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.swap.model.AmountType
import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.feature.R
import java.math.BigDecimal

@Composable
fun MakeViewItem(
    make: Make,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TokenPairIcon(
                    maker = make.makerToken,
                    taker = make.takerToken,
                )
                Text(
                    text = "From: ${make.makeId}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TagViewItem(
                    icon = ImageVector.vectorResource(R.drawable.outline_wallet_24),
                    text = "1 BNB =\n1000.00 USDT",
                    enabled = false,
                )

                TagViewItem(
                    icon = ImageVector.vectorResource(R.drawable.outline_wallet_24),
                    text = "1000.00 USDT",
                    enabled = false,
                )
            }
        }
    }
}


@Preview
@Composable
private fun MakeViewItemPreview() {
    AppTheme {
        fun createFakeToken(
            symbol: String,
            blockchain: Blockchain
        ): Token {
            val coin = Coin(
                symbol = symbol,
                name = when (symbol) {
                    "BTC" -> "Bitcoin"
                    "ETH" -> "Ethereum"
                    "BNB" -> "Binance Coin"
                    "LTC" -> "Litecoin"
                    else -> symbol
                },
                iconUrl = when (symbol) {
                    "BTC" -> "https://assets.coingecko.com/coins/images/1/small/bitcoin.png"
                    "ETH" -> "https://assets.coingecko.com/coins/images/279/small/ethereum.png"
                    "BNB" -> "https://assets.coingecko.com/coins/images/825/small/bnb-icon2_2x.png"
                    "LTC" -> "https://assets.coingecko.com/coins/images/2/small/litecoin.png"
                    else -> ""
                }
            )

            return Token(
                coin = coin,
                contractAddress = if (symbol != "BTC" && symbol != "LTC") "0x123...abc" else null,
                blockchain = blockchain,
                decimal = 18
            )
        }

        val makerToken = createFakeToken("BTC", Blockchain.Ethereum)
        val takerToken = createFakeToken("ETH", Blockchain.Bitcoin)
        MakeViewItem(
            make = Make(
                makeId = "makeId",
                makerId = "makerId",
                makerToken = makerToken,
                takerToken = takerToken,
                refundAddress = "maker-refund-address",
                redeemAddress = "maker-redeem-address",
                priceType = PriceType.Fixed(BigDecimal.TEN),
                isOn = true,
                adAmount = BigDecimal.TEN,
                reservedAmount = BigDecimal.ZERO,
                refundTime = 0L,
                timestamp = System.currentTimeMillis(),
            )

        ) { }
    }
}