package com.exxlexxlee.atomicswap.feature.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.exxlexxlee.atomicswap.core.swap.model.Token
import com.exxlexxlee.atomicswap.feature.R

@Composable
fun TokenPairIcon(
    maker: Token,
    taker: Token,
) {
    Box(modifier = Modifier.height(64.dp).width(80.dp)) {
        TokenIcon(token = maker, modifier = Modifier.align(Alignment.CenterStart))
        TokenIcon(token = taker, modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun TokenIcon(token: Token, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(token.coin.iconUrl)
                .crossfade(true)
                .placeholder(R.drawable.outline_paid_24)
                .error(com.exxlexxlee.atomicswap.core.common.R.drawable.outline_error_24)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape)
                .align(Alignment.CenterStart)
            ,
        )
        AsyncImage(
            model = token.blockchain.iconUrl,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary),
                    shape = CircleShape
                ).align(Alignment.BottomEnd),
        )
    }

}