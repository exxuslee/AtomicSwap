package com.exxlexxlee.atomicswap.feature.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    onDismiss: () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = onClick
            )
            .then(modifier)
            .padding(bottom = 8.dp, top = 2.dp),
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TokenIcon(null)
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
                        text = "ETH",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                    )
                    HSpacer(0.dp)
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Badge(text = "ERC-20")
                    if (token?.blockchain?.isMain == false) TestNetBadge()
                }

            }
        }
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(id = com.exxlexxlee.atomicswap.feature.R.drawable.outline_arrow_drop_down_circle_24),
            contentDescription = "token selector",
        )

    }
}