package com.example.atomicswap.core.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.common.R

data class ContentCell (
    val header: String,
    val index: Int,
    val clipModifier: Modifier,
    val borderModifier: Modifier,
)
@Composable
fun LazyStickyHeaderColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState(0, 0) },
    items: Map<String, List<Long>>,
    revealCardId: Long,
    onClick: (Long) -> Unit,
    onReveal: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    onBottomReached: () -> Unit,
    content: @Composable RowScope.(ContentCell) -> Unit,
) {
    LazyColumn(modifier, state) {
        val txList = items.values.flatten()
        val index = if (txList.size > 2) txList.size - 2 else 0
        val bottomReachedId = txList.getOrNull(index)

        items.forEach { (dateHeader, transactions) ->
            stickyHeader { HeaderStick(text = dateHeader) }

            val itemsCount = transactions.size
            val singleElement = itemsCount == 1
            itemsIndexed(transactions, key = { index, item -> "$item-$index" }) { index, item ->
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
                            .padding(horizontal = 12.dp)
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
                                    onDelete.invoke(item)
                                },
                                content = {
                                    Image(
                                        painter = painterResource(id = R.drawable.outline_delete_24),
                                        contentDescription = "delete",
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onErrorContainer)
                                    )
                                })
                        }
                    }
                    DraggableCardSimple(
                        isRevealed = revealCardId == item,
                        cardOffset = 72f,
                        onReveal = {
                            if (revealCardId != item) onReveal.invoke(item)
                        },
                        onCancel = {
                            onReveal.invoke(-1)
                        },
                        content = {
                            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                                val divider =
                                    position == SectionItemPosition.Middle || position == SectionItemPosition.Last
                                SectionUniversalItem(
                                    borderTop = divider,
                                ) {
                                    val borderModifier =
                                        if (position != SectionItemPosition.Single) {
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
                                                onClick = {
                                                    onClick.invoke(item)
                                                },
                                            )
                                            .onGloballyPositioned { coordinates ->
                                                rowHeightPx = coordinates.size.height
                                            }
                                            .padding(top = 1.dp),
                                        verticalPadding = 0.dp,
                                    ) {
                                        content.invoke(
                                            this,
                                            ContentCell(
                                                dateHeader,
                                                index,
                                                clipModifier,
                                                borderModifier,
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    )
                }

                if (item == bottomReachedId) onBottomReached.invoke()
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

    }

}