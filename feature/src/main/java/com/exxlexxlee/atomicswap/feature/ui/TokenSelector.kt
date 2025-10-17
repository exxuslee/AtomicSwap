package com.exxlexxlee.atomicswap.feature.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.ui.HSpacer
import com.exxlexxlee.atomicswap.core.swap.model.Token

@Composable
fun TokenSelector(
    modifier: Modifier,
    token: Token?,
    placeholder: String,
    isMainNet: Boolean = true,
    expanded: Boolean = false,
    onClick: () -> Unit,
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (token != null) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
    ) {
        val rotation by animateFloatAsState(
            targetValue = if (expanded) 180f else 0f,
            animationSpec = tween(durationMillis = 300),
            label = "rotationAnimation"
        )
        Row(
            modifier = Modifier.clickable(onClick = onClick).padding(4.dp),
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TokenIcon(token)
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = token?.coin?.symbol ?: placeholder,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                        )
                        HSpacer(0.dp)
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        token?.badge()?.let { Badge(text = it) }
                        if (!isMainNet) TestNetBadge()
                    }

                }
            }
            val icon = token?.let {
                com.exxlexxlee.atomicswap.feature.R.drawable.outline_cancel_24
            } ?: com.exxlexxlee.atomicswap.feature.R.drawable.outline_arrow_drop_down_circle_24
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer {
                        rotationZ = rotation
                    },
                painter = painterResource(id = icon),
                contentDescription = "token selector",
            )
        }
    }

}