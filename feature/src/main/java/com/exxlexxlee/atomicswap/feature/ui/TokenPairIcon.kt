package com.exxlexxlee.atomicswap.feature.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.swap.model.Token

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

