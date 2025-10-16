package com.exxlexxlee.atomicswap.feature.tabs.common.newmake

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models.ViewState
import com.exxlexxlee.atomicswap.feature.ui.TokenSelector


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMakeView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        RowUniversal {
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