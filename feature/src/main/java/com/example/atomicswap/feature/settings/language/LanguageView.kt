package com.example.atomicswap.feature.settings.language

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.ui.component.CellUniversalLawrenceSection
import com.example.atomicswap.core.ui.component.RowUniversal
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.language.models.Event
import com.example.atomicswap.feature.settings.language.models.ViewState

@Composable
fun LanguageView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(12.dp))
        CellUniversalLawrenceSection(viewState.languageItems) { item ->
            LanguageCell(
                title = item.name,
                subtitle = item.nativeName,
                icon = item.icon,
                checked = item.current,
                onClick = { eventHandler.invoke(Event.Select(item.localeType)) }
            )
        }
        Spacer(Modifier.height(24.dp))
    }

}

@Composable
private fun LanguageCell(
    title: String,
    subtitle: String,
    icon: Int,
    checked: Boolean,
    onClick: () -> Unit
) {
    RowUniversal(
        onClick = onClick
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(32.dp),
            painter = painterResource(icon),
            contentDescription = null
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(title)
            Spacer(Modifier.height(1.dp))
            Text(subtitle)
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
fun TermsView_Preview() {
    AppTheme {
        LanguageView(
            viewState = ViewState(),
            eventHandler = { }
        )
    }
}