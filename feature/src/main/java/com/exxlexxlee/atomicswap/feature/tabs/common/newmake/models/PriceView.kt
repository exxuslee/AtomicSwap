package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.ui.HSpacer
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.core.common.ui.VSpacer
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.ui.TagViewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceView(
    viewState: ViewState, eventHandler: (Event) -> Unit
) {
    fun hasBothTickers() =
        viewState.make.makerToken != null && viewState.make.takerToken != null

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        val context = LocalContext.current
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
        ) {
            TagViewItem(
                icon = ImageVector.vectorResource(R.drawable.outline_store_24),
                text = stringResource(R.string.market_price),
                enabled = hasBothTickers(),
            )
            if (hasBothTickers()) {
                val takerTicker = viewState.make.takerToken?.coin?.symbol ?: ""
                val makerTicker = viewState.make.makerToken?.coin?.symbol ?: ""
                val price = "1 $makerTicker = ${viewState.price} $takerTicker"
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp).weight(1f),
                    text = price,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                )
            }
        }
        if (hasBothTickers()) RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HSpacer(0.dp)
            Text(
                modifier = Modifier.weight(1f),
                text = "Discount: ${viewState.make.discount}%",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            HsIconButton(onClick = {

            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.outline_refresh_24),
                    contentDescription = "refresh"
                )
            }
        }
        RowUniversal(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.outline_percent_discount_24),
                contentDescription = "refresh"
            )
            var wrapUp: Boolean by rememberSaveable { mutableStateOf(false) }
            val sliderState =
                rememberSliderState(
                    value = inverseGoldenRatio(viewState.make.discount),
                    valueRange = -6f..6f,
                    steps = 11,
                    onValueChangeFinished = { wrapUp = !wrapUp },
                )
            LaunchedEffect(wrapUp) {
                if (!hasBothTickers()) {
                    Toast.makeText(context, "Please, select tickers first", Toast.LENGTH_SHORT)
                        .show()
                    sliderState.value = 0f
                } else eventHandler.invoke(Event.SetDiscount(sliderState.value))
            }
            val interactionSource = remember { MutableInteractionSource() }
            Slider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(28.dp),
                state = sliderState,
                interactionSource = interactionSource,
                thumb = { SliderDefaults.Thumb(interactionSource = interactionSource) },
                track = {
                    SliderDefaults.Track(
                        enabled = hasBothTickers(),
                        sliderState = sliderState
                    )
                }
            )
        }

        VSpacer(8.dp)
    }
}

private fun inverseGoldenRatio(value: Int): Float = when (value) {
    13 -> 6f
    8 -> 5f
    5 -> 4f
    3 -> 3f
    2 -> 2f
    1 -> 1f
    0 -> 0f
    -1 -> -1f
    -2 -> -2f
    -3 -> -3f
    -5 -> -4f
    -8 -> -5f
    -13 -> -6f
    else -> 0f
}