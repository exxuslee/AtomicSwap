package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.ui.HSpacer
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.core.common.ui.VSpacer
import com.exxlexxlee.atomicswap.core.swap.model.PriceType
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.ui.TagViewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceView(
    viewState: ViewState, eventHandler: (Event) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            TagViewItem(
                icon = ImageVector.vectorResource(R.drawable.outline_sell_24),
                text = stringResource(R.string.price_fixed),
                enabled = viewState.make.priceType is PriceType.Fixed,
            )
            TagViewItem(
                icon = ImageVector.vectorResource(R.drawable.outline_store_24),
                text = stringResource(R.string.price_market),
                enabled = viewState.make.priceType is PriceType.Market,
            )
        }
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HSpacer(0.dp)
            Text(
                text = "1 BNB = 1000 USDT",
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
            val sliderState =
                rememberSliderState(
                    value = 0f,
                    valueRange = -6f..6f,
                    steps = 11,
                    onValueChangeFinished = {
                        // launch some business logic update with the state you hold
                        // viewModel.updateSelectedSliderValue(sliderPosition)
                    },
                )
            val interactionSource = remember { MutableInteractionSource() }
            Slider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(28.dp),
                state = sliderState,
                interactionSource = interactionSource,
                thumb = { SliderDefaults.Thumb(interactionSource = interactionSource) },
                track = {
                    SliderDefaults.Track(enabled = false, sliderState = sliderState)
                }
            )
        }
        VSpacer(8.dp)
    }
}