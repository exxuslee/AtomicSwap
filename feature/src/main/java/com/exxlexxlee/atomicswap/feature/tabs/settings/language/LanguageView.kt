package com.exxlexxlee.atomicswap.feature.tabs.settings.language

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.CellUniversalLawrenceSection
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.tabs.settings.language.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.settings.language.models.ViewState
import com.hwasfy.localize.api.LanguageManager
import com.hwasfy.localize.api.currentAppLocale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    val scrollState = rememberScrollState()
    val navController = LocalNavController.current
    val localContext = LocalContext.current

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(12.dp))
        CellUniversalLawrenceSection(viewState.languageItems) { item ->
            LanguageCell(
                title = item.locale.displayLanguage,
                subtitle = item.locale.displayName,
                icon = item.icon,
                checked = currentAppLocale() == item,
                onClick = {
                    LanguageManager.setLanguage(localContext, item)
                    navController.popBackStack()
                }
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
                .padding(horizontal = 12.dp)
                .size(32.dp),
            painter = painterResource(icon),
            contentDescription = title
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