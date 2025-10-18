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
import androidx.compose.runtime.mutableFloatStateOf
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
fun AvailableView(
    viewState: ViewState, eventHandler: (Event) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        val context = LocalContext.current
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TagViewItem(
                icon = ImageVector.vectorResource(R.drawable.outline_open_in_full_24),
                text = stringResource(R.string.available),
                enabled = viewState.make.adBalance != null,
            )
            Text(
                text = "Balance: 0.001 BNB",
                style = MaterialTheme.typography.titleMedium,
            )
        }
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            HSpacer(0.dp)
            Text(
                text = "0.001 BNB",
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
                imageVector = ImageVector.vectorResource(R.drawable.outline_sell_24),
                contentDescription = "discount"
            )
            Text(
                text = ":",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            var wrapUp: Boolean? by rememberSaveable { mutableStateOf(null) }
            val sliderState =
                rememberSliderState(
                    value = 1f,
                    valueRange = 0.05f..1f,
                    steps = 11,
                    onValueChangeFinished = { wrapUp = !(wrapUp?:false) },
                )
            LaunchedEffect(wrapUp) {
                if (wrapUp == null) return@LaunchedEffect
                if (viewState.make.makerToken == null) {
                    Toast.makeText(context, "Please, select maker ticker first", Toast.LENGTH_SHORT)
                        .show()
                    sliderState.value = 1f
                } else eventHandler.invoke(Event.SetReserve(sliderState.value))
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
                        enabled = viewState.make.makerToken != null,
                        sliderState = sliderState
                    )
                }
            )
        }
        VSpacer(8.dp)
    }
}