package com.exxlexxlee.atomicswap.feature.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.swap.model.AmountType
import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.core.swap.model.Coin
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.feature.R
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TokenPairIcon(
                    makerIconUrl = make.makerToken.coin.iconUrl,
                    takerIconUrl = make.takerToken.coin.iconUrl,
                )
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TagViewItem(
                        icon = painterResource(R.drawable.outline_wallet_24),
                        text = "1.00 BNB"
                    )
                    TagViewItem(
                        icon = painterResource(R.drawable.outline_wallet_24),
                        text = "1000.00 USDT"
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TagViewItem(
                    icon = painterResource(R.drawable.outline_wallet_24),
                    text = "1 BNB = 1000.00 USDT"
                )
                TagViewItem(
                    icon = painterResource(R.drawable.outline_wallet_24),
                    text = "1.00 BNB"
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: ${make.makeId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formatDateTime(make.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


private fun formatDateTime(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    return formatter.format(date)
}

@Composable
private fun TokenPairIcon(
    makerIconUrl: String,
    takerIconUrl: String,
) {
    Box(modifier = Modifier.size(44.dp)) {
        TokenIcon(url = makerIconUrl, modifier = Modifier.align(Alignment.CenterStart))
        TokenIcon(url = takerIconUrl, modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
private fun TokenIcon(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier.size(32.dp),
        contentScale = ContentScale.Crop
    )
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
                id = "token-$symbol",
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
                id = symbol,
                coin = coin,
                contractAddress = if (symbol != "BTC" && symbol != "LTC") "0x123...abc" else null,
                blockchain = blockchain,
                decimal = 18
            )
        }

        val makerToken = createFakeToken("BTC", Blockchain.Ethereum(isMain = true))
        val takerToken = createFakeToken("ETH", Blockchain.Bitcoin(isMain = true))
        MakeViewItem(
            make = Make(
                makeId = "makeId",
                makerId = "makerId",
                makerToken = makerToken,
                takerToken = takerToken,
                refundAddress = "maker-refund-address",
                redeemAddress = "maker-redeem-address",
                amount = AmountType.ExactIn(
                    makerExactAmount = BigDecimal("10.0"),
                    takerStartAmount = BigDecimal("1.6")
                ),
                priceType = PriceType.Fixed,
                isOn = true,
                reservedAmount = BigDecimal.ZERO,
                refundTime = 0L,
                timestamp = System.currentTimeMillis(),
            )

        ) { }
    }
}