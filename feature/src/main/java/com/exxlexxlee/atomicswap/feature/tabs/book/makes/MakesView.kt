package com.exxlexxlee.atomicswap.feature.tabs.book.makes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.ListEmptyView
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.ui.MakeViewItem
import com.exxlexxlee.atomicswap.feature.ui.TokenSelector
import com.exxlexxlee.atomicswap.feature.navigation.Routes
import com.exxlexxlee.atomicswap.feature.tabs.book.makes.models.Event
import com.exxlexxlee.atomicswap.feature.tabs.book.makes.models.ViewState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakesView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    val navController = LocalNavController.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (viewState.makes.isEmpty()) {
            ListEmptyView(
                text = stringResource(R.string.make_empty_list),
                icon = R.drawable.outline_empty_dashboard_24
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stickyHeader {
                    RowUniversal {
                        TokenSelector(
                            modifier = Modifier
                                .weight(1f),
                            token = viewState.filterToken.first,
                            expanded = viewState.expandedTaker,
                            placeholder = stringResource(com.exxlexxlee.atomicswap.core.common.R.string.from),
                        ) {
                            if (viewState.filterToken.first != null) {
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
                            token = viewState.filterToken.second,
                            expanded = viewState.expandedMaker,
                            placeholder = stringResource(com.exxlexxlee.atomicswap.core.common.R.string.to),
                        ) {
                            if (viewState.filterToken.second != null) {
                                eventHandler.invoke(Event.MakerToken(null))
                            } else eventHandler.invoke(Event.MakerTokenSheet)
                        }

                    }
                }

                items(viewState.makes) { make ->
                    MakeViewItem(make) {
                        navController.navigate(Routes.ChronicleRoute.SwapRoute.createRoute(make.makeId))

                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun MakeView_Preview() {
    AppTheme {
        MakesView(
            viewState = ViewState(),
            eventHandler = {},
        )
    }
}


