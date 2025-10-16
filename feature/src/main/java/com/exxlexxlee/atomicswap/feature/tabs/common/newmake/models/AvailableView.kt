package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models

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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
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
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.ui.TagViewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvailableView(

) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TagViewItem(
                icon = ImageVector.vectorResource(R.drawable.outline_open_in_full_24),
                text = stringResource(R.string.available)
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
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val sliderState = rememberSliderState(
                value = 0f,
                valueRange = -6f..6f,
                steps = 11
            )
            Slider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(28.dp),
                state = sliderState
            )
        }
        VSpacer(8.dp)
    }
}