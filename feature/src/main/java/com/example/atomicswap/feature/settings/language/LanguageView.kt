package com.example.atomicswap.feature.settings.language

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.ui.component.CellUniversalLawrenceSection
import com.example.atomicswap.core.ui.component.RowUniversal
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.language.models.Event
import com.example.atomicswap.feature.settings.language.models.ViewState
import com.hwasfy.localize.api.currentAppLocale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    val scrollState = rememberScrollState()

    Column {
        TopAppBar(
            windowInsets = WindowInsets(0, 0, 0, 0),
            title = {
                Text(
                    text = stringResource(R.string.language),
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    eventHandler.invoke(Event.PopBackStack)
                }) {
                    Icon(
                        painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))
            CellUniversalLawrenceSection(viewState.languageItems) { item ->
                LanguageCell(
                    title = item.locale.displayLanguage,
                    subtitle = item.locale.displayName,
                    icon = item.icon,
                    checked = currentAppLocale() == item,
                    onClick = { eventHandler.invoke(Event.Select(item)) }
                )
            }
            Spacer(Modifier.height(24.dp))
        }
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