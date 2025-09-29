package com.exxlexxlee.atomicswap.feature.settings.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.base.DateHelper
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.core.common.ui.LazyStickyHeaderColumn
import com.exxlexxlee.atomicswap.core.common.ui.ListEmptyView
import com.exxlexxlee.atomicswap.core.common.ui.TopAppBar
import com.exxlexxlee.atomicswap.domain.model.Notification
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.settings.notification.models.Event
import com.exxlexxlee.atomicswap.feature.settings.notification.models.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column {
        TopAppBar(stringResource(R.string.notifications)) {
            eventHandler.invoke(Event.PopBackStack)
        }

        if (viewState.items.isEmpty()) {
            ListEmptyView(
                text = stringResource(R.string.notification_empty_list),
                icon = R.drawable.outline_empty_dashboard_24
            )
        } else {
            LazyStickyHeaderColumn(
                items = viewState.items.mapValues { (_, list) -> list.map { it.id } },
                revealCardId = viewState.revealCardId,
                onClick = { id -> eventHandler.invoke(Event.Click(id)) },
                onReveal = { id -> eventHandler.invoke(Event.Reveal(id)) },
                onDelete = { id -> eventHandler.invoke(Event.Delete(id)) },
                onBottomReached = { eventHandler.invoke(Event.BottomReached) }
            ) { contentCell ->
                NotificationsCell(
                    modifier = contentCell.clipModifier,
                    borderModifier = contentCell.borderModifier,
                    viewState.items[contentCell.header]?.get(contentCell.index)!!
                )
            }
        }
    }
}

@Composable
private fun NotificationsCell(
    modifier: Modifier,
    borderModifier: Modifier,
    item: Notification,
) {
    Column(
        modifier = modifier
            .background(
                if (item.isRead) MaterialTheme.colorScheme.surface
                else MaterialTheme.colorScheme.surfaceContainer
            )
            .then(borderModifier)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row {
            Text(
                text = item.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = DateHelper.formatTime(item.id),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )

        }
        Spacer(Modifier.height(1.dp))
        Row {
            Text(
                text = item.body,
                modifier = Modifier.weight(1f),
                maxLines = 2,
            )
        }
    }
}

@Preview
@Composable
fun NotificationView_Preview() {
    AppTheme {
        NotificationView(
            viewState = ViewState(),
            eventHandler = { }
        )
    }
}