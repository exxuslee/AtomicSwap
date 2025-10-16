package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.ViewState
import com.exxlexxlee.atomicswap.feature.ui.TokenSelector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMakeView(viewState: ViewState, eventHandler: (Event) -> Unit) {

    Card(
        modifier = Modifier.padding(8.dp)
    ) {
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            TokenSelector(
                modifier = Modifier
                    .weight(1f),
                token = viewState.tokenPair.first,
                expanded = viewState.expandedTaker,
                placeholder = stringResource(R.string.title_taker),
            ) {
                if (viewState.tokenPair.first != null) {
                    eventHandler.invoke(Event.TakerToken(null))
                } else eventHandler.invoke(Event.TakerTokenSheet)
            }

            IconButton(
                onClick = {
                    eventHandler.invoke(Event.SwitchToken)
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.outline_arrow_right_alt_24),
                    contentDescription = "token selector",
                )
            }

            TokenSelector(
                modifier = Modifier
                    .weight(1f),
                token = viewState.tokenPair.second,
                expanded = viewState.expandedMaker,
                placeholder = stringResource(R.string.title_maker),
            ) {
                if (viewState.tokenPair.second != null) {
                    eventHandler.invoke(Event.MakerToken(null))
                } else eventHandler.invoke(Event.MakerTokenSheet)
            }

        }
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "Price",
                )
                RowUniversal(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_sell_24),
                        contentDescription = "Price"
                    )
                    Text("1 BNB = 1000 USDT")
                }
                RowUniversal(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Slider(
                        state = rememberSliderState()
                    )
                }
            }
        }
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "Available",
                )
                RowUniversal(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.outline_open_in_full_24),
                        contentDescription = "Available"
                    )
                    HsIconButton(
                        onClick = {}
                    ) {
                        Text("max")
                    }
                }
                RowUniversal(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Slider(
                        state = rememberSliderState()
                    )
                }


            }
        }
    }

}

@Preview
@Composable
fun AggregatorView_Preview() {
    AppTheme {
        NewMakeView(
            viewState = ViewState("makeId"),
            eventHandler = { }
        )
    }
}