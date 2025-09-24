package com.example.atomicswap.feature.settings

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.ui.component.CellUniversalSection
import com.example.atomicswap.core.ui.component.HsRow
import com.example.atomicswap.core.ui.component.RowUniversal
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.models.SettingsEvent
import com.example.atomicswap.feature.settings.models.SettingsViewState
import com.example.atomicswap.core.ui.component.VSpacer

@Composable
fun SettingsView(viewState: SettingsViewState, eventHandler: (SettingsEvent) -> Unit) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_settings),
            style = MaterialTheme.typography.headlineMedium
        )
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
        VSpacer(16.dp)
        Text(
            text = stringResource(R.string.title_settings),
            style = MaterialTheme.typography.headlineMedium
        )
        CellUniversalSection(
            listOf(
                {
                    HsRow(
                        icon = Icons.Outlined.Email,
                        titleContent = {
                            Text(
                                "irynalinnik@gmail.com",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {},
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        icon = Icons.Outlined.Phone,
                        titleContent = {
                            Text(
                                "+380(66)372-71-02",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {},
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        icon = Icons.Outlined.Phone,
                        titleContent = {
                            Text(
                                "t.me/irynalinnik_visualizer",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {},
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        icon = Icons.Outlined.Phone,
                        titleContent = {
                            Text(
                                "www.instagram.com/irinalinnik01/",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {},
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        icon = Icons.Outlined.Phone,
                        titleContent = {
                            Text(
                                "www.behance.net/irinalinnik",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {},
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        icon = Icons.Outlined.Phone,
                        titleContent = {
                            Text(
                                "www.linkedin.com/in/irinalinnik-visualizer/",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {},
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        icon = Icons.Outlined.Phone,
                        titleContent = {
                            Text(
                                "www.linkedin.com/in/irinalinnik-visualizer/",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {
                            eventHandler.invoke(SettingsEvent.IsDark(!viewState.isDark))
                        },
                        arrowRight = false,
                    )
                },
            )
        )
        VSpacer(32.dp)
    }
}

@PreviewLightDark
@Composable
fun ProfileView_Preview() {
    AppTheme {
        SettingsView(
            viewState = SettingsViewState(),
            eventHandler = { }
        )
    }
}