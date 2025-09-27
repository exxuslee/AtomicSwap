package com.example.atomicswap.feature.settings.notification

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.common.ui.DraggableCardSimple
import com.example.atomicswap.core.common.ui.HeaderStick
import com.example.atomicswap.core.common.ui.HsIconButton
import com.example.atomicswap.core.common.ui.ListEmptyView
import com.example.atomicswap.core.common.ui.RowUniversal
import com.example.atomicswap.core.common.ui.SectionItemPosition
import com.example.atomicswap.core.common.ui.SectionUniversalItem
import com.example.atomicswap.core.common.ui.sectionItemBorder
import com.example.atomicswap.core.common.theme.AppTheme
import com.example.atomicswap.domain.model.Notification
import com.example.atomicswap.feature.R
import com.example.atomicswap.feature.settings.notification.models.Event
import com.example.atomicswap.feature.settings.notification.models.ViewState
import com.example.atomicswap.core.common.base.DateHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationView(viewState: ViewState, eventHandler: (Event) -> Unit) {
    Column {
        TopAppBar(
            windowInsets = WindowInsets(0, 0, 0, 0),
            title = {
                Text(
                    text = stringResource(R.string.notifications),
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
        if (viewState.items.isEmpty()) {
            ListEmptyView(
                text = stringResource(R.string.notification_empty_list),
                icon = R.drawable.outline_empty_dashboard_24
            )
        } else {
            val listState = rememberSaveable(saver = LazyListState.Saver) {
                LazyListState(0, 0)
            }

            LazyColumn(state = listState) {
                transactionList(
                    eventHandler = eventHandler,
                    viewState,
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.transactionList(
    eventHandler: (Event) -> Unit,
    uiState: ViewState,
) {
    val bottomReachedUid = getBottomReachedUid(uiState.items)

    uiState.items.forEach { (dateHeader, transactions) ->
        stickyHeader { HeaderStick(text = dateHeader) }

        val itemsCount = transactions.size
        val singleElement = itemsCount == 1
        itemsIndexed(transactions, key = { a, b -> b.id + a }) { index, item ->
            val position: SectionItemPosition = when {
                singleElement -> SectionItemPosition.Single
                index == 0 -> SectionItemPosition.First
                index == itemsCount - 1 -> SectionItemPosition.Last
                else -> SectionItemPosition.Middle
            }

            val clipModifier = when (position) {
                SectionItemPosition.First -> {
                    Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                }

                SectionItemPosition.Last -> {
                    Modifier.clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                }

                SectionItemPosition.Single -> {
                    Modifier.clip(RoundedCornerShape(12.dp))
                }

                else -> Modifier
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                var rowHeightPx by remember { mutableIntStateOf(0) }
                val heightInDp = with(LocalDensity.current) { rowHeightPx.toDp() - 2.dp }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightInDp)
                        .padding(horizontal = 18.dp)
                        .then(clipModifier),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        HsIconButton(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(64.dp),
                            onClick = {
                                eventHandler.invoke(Event.Delete(item.id))
                            },
                            content = {
                                Image(
                                    modifier = Modifier
                                        .width(22.dp)
                                        .height(22.dp),
                                    painter = painterResource(id = R.drawable.outline_delete_24),
                                    contentDescription = "delete",
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onErrorContainer)
                                )
                            })
                    }
                }
                DraggableCardSimple(
                    isRevealed = uiState.revealCardId == item.id,
                    cardOffset = 72f,
                    onReveal = {
                        if (uiState.revealCardId != item.id) eventHandler.invoke(
                            Event.RevealCardIndex(item.id)
                        )
                    },
                    onCancel = {
                        eventHandler.invoke(Event.RevealCardIndex(-1))
                    },
                    content = {
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            TransactionCell(
                                item, position, clipModifier,
                                onClick = {
                                    eventHandler.invoke(Event.OnClick(item.id))
                                },
                                onHeight = { rowHeightPx = it })
                        }
                    }
                )
            }

            if (item.id == bottomReachedUid) eventHandler.invoke(Event.OnBottomReached)

        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
    }

    item { Spacer(modifier = Modifier.height(20.dp)) }
}

private fun getBottomReachedUid(transactionsMap: Map<String, List<Notification>>): Long? {
    val txList = transactionsMap.values.flatten()
    val index = if (txList.size > 2) txList.size - 2 else 0
    return txList.getOrNull(index)?.id
}

@Composable
private fun TransactionCell(
    item: Notification,
    position: SectionItemPosition,
    modifier: Modifier,
    onClick: () -> Unit,
    onHeight: (Int) -> Unit,
) {
    val divider = position == SectionItemPosition.Middle || position == SectionItemPosition.Last
    SectionUniversalItem(
        borderTop = divider,
    ) {
        val borderModifier = if (position != SectionItemPosition.Single) {
            Modifier.sectionItemBorder(
                1.dp,
                MaterialTheme.colorScheme.primaryContainer,
                12.dp,
                position
            )
        } else {
            Modifier.border(
                1.dp,
                MaterialTheme.colorScheme.primaryContainer,
                RoundedCornerShape(12.dp)
            )
        }

        RowUniversal(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
                .clickable(
                    onClick = onClick,
                    enabled = !item.isRead
                )
                .onGloballyPositioned { coordinates ->
                    onHeight(coordinates.size.height)
                },
            verticalPadding = 0.dp,
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 1.dp)
                    .background(
                        if (item.isRead) MaterialTheme.colorScheme.onSurfaceVariant
                        else MaterialTheme.colorScheme.onSurface
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
                        text = DateHelper.formatTime( item.id),
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