package com.example.atomicswap.feature.settings.main

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.ui.component.CellUniversalSection
import com.example.atomicswap.core.ui.component.HsRow
import com.example.atomicswap.core.ui.component.RowUniversal
import com.example.atomicswap.core.ui.component.VSpacer
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.main.models.SettingsEvent
import com.example.atomicswap.feature.settings.main.models.SettingsViewState

@Composable
fun SettingsView(
    viewState: SettingsViewState,
    eventHandler: (SettingsEvent) -> Unit,
) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState),
    ) {
        RowUniversal(
            verticalPadding = 16.dp,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                bitmap = viewState.avatar.asImageBitmap(),
                contentDescription = "avatar",
                modifier = Modifier
                    .height(180.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
        }
        CellUniversalSection {
            Text(
                text = stringResource(R.string.account),
                style = MaterialTheme.typography.titleLarge
            )
        }

        CellUniversalSection(
            listOf(
                {
                    HsRow(
                        iconRes = R.drawable.outline_wallet_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.wallet_connect),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        arrowRight = false,
                    ) {
                        Switch(
                            checked = viewState.isWalletConnect,
                            onCheckedChange = { },
                            colors = SwitchDefaults.colors()
                        )
                    }
                },
                {
                    HsRow(
                        iconRes = R.drawable.outline_currency_exchange_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.price_aggregator),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {},
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        iconRes = R.drawable.outline_database_off_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.clear_local_storage),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {},
                        arrowRight = true,
                    )
                },
            )
        )
        VSpacer(16.dp)

        CellUniversalSection {
            Text(
                text = stringResource(R.string.app),
                style = MaterialTheme.typography.titleLarge
            )
        }
        CellUniversalSection(
            listOf(
                {
                    HsRow(
                        iconRes = R.drawable.outline_language_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.language),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {
                            eventHandler.invoke(SettingsEvent.OpenLanguageScreen)
                        },
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        iconRes = R.drawable.outline_routine_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.label_dark_mode),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        arrowRight = false,
                    ) {
                        Switch(
                            checked = viewState.isDark,
                            onCheckedChange = { eventHandler.invoke(SettingsEvent.IsDark(!viewState.isDark)) },
                            colors = SwitchDefaults.colors()
                        )
                    }
                },
                {
                    HsRow(
                        iconRes = R.drawable.outline_contract_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.terms_of_service),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {
                            eventHandler.invoke(SettingsEvent.OpenTermsScreen)
                        },
                        arrowRight = true,
                    )
                },
            )
        )
        VSpacer(32.dp)
    }
}

@Preview
@Composable
fun ProfileView_Preview() {
    AppTheme {
        SettingsView(
            viewState = SettingsViewState(),
            eventHandler = {},
        )
    }
}