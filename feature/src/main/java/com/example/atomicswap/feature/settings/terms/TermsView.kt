package com.example.atomicswap.feature.settings.terms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.ui.component.CellUniversalSection
import com.example.atomicswap.core.ui.component.HsRow
import com.example.atomicswap.core.ui.component.RowUniversal
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.main.models.SettingsEvent
import com.example.atomicswap.feature.settings.main.models.SettingsViewState
import com.example.atomicswap.core.ui.component.VSpacer
import com.example.atomicswap.feature.settings.terms.models.Event
import com.example.atomicswap.feature.settings.terms.models.ViewState

@Composable
fun TermsView(viewState: ViewState, eventHandler: (Event) -> Unit) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Terms of Service",
            style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(top = 16.dp)
        )

        VSpacer(32.dp)
    }
}

@Preview
@Composable
fun ProfileView_Preview() {
    AppTheme {
        TermsView(
            viewState = ViewState(),
            eventHandler = { }
        )
    }
}