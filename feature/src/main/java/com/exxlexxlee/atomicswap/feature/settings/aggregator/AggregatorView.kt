package com.exxlexxlee.atomicswap.feature.settings.aggregator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.CellUniversalLawrenceSection
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.core.common.ui.TopAppBar
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.settings.aggregator.models.Event
import com.exxlexxlee.atomicswap.feature.settings.aggregator.models.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AggregatorView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    val scrollState = rememberScrollState()

    Column {
        TopAppBar(stringResource(R.string.language)) {
            eventHandler.invoke(Event.PopBackStack)
        }
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(12.dp))
            CellUniversalLawrenceSection(viewState.emitters) { item ->
                AggregatorCell(
                    title = item.label,
                    icon = item.icon,
                    checked = viewState.selected == item,
                    onClick = { eventHandler.invoke(Event.Select(item.label)) },
                )
            }
            Spacer(Modifier.height(24.dp))
        }
    }

}

@Composable
private fun AggregatorCell(
    title: String,
    icon: Int,
    checked: Boolean,
    onClick: () -> Unit
) {
    RowUniversal(
        onClick = onClick
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(32.dp),
            painter = painterResource(icon),
            contentDescription = null
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(title)
        }
        Box(
            modifier = Modifier
                .width(52.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    painter = painterResource(R.drawable.outline_check_24),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun AggregatorView_Preview() {
    AppTheme {
        AggregatorView(
            viewState = ViewState(),
            eventHandler = { }
        )
    }
}