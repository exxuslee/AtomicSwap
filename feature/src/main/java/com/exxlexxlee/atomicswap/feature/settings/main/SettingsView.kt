package com.exxlexxlee.atomicswap.feature.settings.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.BadgeType
import com.exxlexxlee.atomicswap.core.common.ui.BadgedIcon
import com.exxlexxlee.atomicswap.core.common.ui.CellUniversalSection
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.HsRow
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.core.common.ui.VSpacer
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.navigation.Routes
import com.exxlexxlee.atomicswap.feature.settings.main.models.Event
import com.exxlexxlee.atomicswap.feature.settings.main.models.ViewState


@Composable
fun SettingsView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    val scrollState = rememberScrollState()
    val navController = LocalNavController.current

    Column(
        modifier = Modifier.verticalScroll(scrollState),
    ) {
        RowUniversal(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalPadding = 16.dp,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Image(
                bitmap = viewState.avatar.asImageBitmap(),
                contentDescription = "avatar",
                modifier = Modifier
                    .height(160.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            )
            HsIconButton(
                onClick = {
                    navController.navigate(Routes.Settings.Notification.route)
                },
            ) {
                BadgedIcon(
                    if (viewState.unreadCount > 0) BadgeType.BadgeNumber(viewState.unreadCount)
                    else null
                ) {
                    Icon(
                        modifier = Modifier.padding(horizontal = 2.dp).size(24.dp),
                        painter = painterResource(id = R.drawable.outline_notifications_24),
                        contentDescription = "notification center",
                    )
                }
            }
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
                            onCheckedChange = { enabled -> eventHandler.invoke(Event.OpenWalletConnectDialog) },
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
                        onClick = {
                            navController.navigate(Routes.Settings.PriceAggregator.route)
                        },
                        arrowRight = true,
                    ) {
                        Icon(
                            painter = painterResource(viewState.priceAggregator.icon),
                            contentDescription = viewState.priceAggregator.label,
                            modifier = Modifier.padding(horizontal = 12.dp),
                        )
                    }
                },
                {
                    val click = { eventHandler.invoke(Event.OpenClearStorageDialog) }
                    HsRow(
                        iconRes = R.drawable.outline_database_off_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.clear_local_storage),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = if (!viewState.isEmptyLocalStorage) click else null,
                        arrowRight = !viewState.isEmptyLocalStorage,
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
                            onCheckedChange = { eventHandler.invoke(Event.IsDark(!viewState.isDark)) },
                            colors = SwitchDefaults.colors()
                        )
                    }
                },
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
                            navController.navigate(Routes.Settings.Language.route)
                        },
                        arrowRight = true,
                    )
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
                            navController.navigate(Routes.Settings.Therms.route)
                        },
                        arrowRight = true,
                    ) {
                        if (!viewState.isTermsOfUseRead) Icon(
                            painter = painterResource(R.drawable.rounded_warning_24),
                            contentDescription = viewState.priceAggregator.label,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                {
                    HsRow(
                        iconRes = R.drawable.outline_volunteer_activism_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.donate),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {
                            navController.navigate(Routes.Settings.Donate.route)
                        },
                        arrowRight = true,
                    )
                },
                {
                    HsRow(
                        iconRes = R.drawable.outline_mobile_info_24,
                        titleContent = {
                            Text(
                                stringResource(R.string.about, stringResource(R.string.app_name)),
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        },
                        onClick = {
                            navController.navigate(Routes.Settings.About.route)
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
            viewState = ViewState(
                isTermsOfUseRead = false,
            ),
            eventHandler = {},
        )
    }
}