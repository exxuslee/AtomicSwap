package com.exxlexxlee.atomicswap.feature.tabs.common.newmake.models

import android.content.ClipData
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.ui.HsIconButton
import com.exxlexxlee.atomicswap.core.common.ui.RowUniversal
import com.exxlexxlee.atomicswap.core.common.ui.VSpacer
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.ui.TagViewItem
import com.reown.android.internal.common.scope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedeemView (
    viewState: ViewState, eventHandler: (Event) -> Unit
) {
    val clipboard = LocalClipboard.current
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TagViewItem(
                icon = ImageVector.vectorResource(R.drawable.outline_input_circle_24),
                text = stringResource(R.string.redeem),
                enabled = viewState.make.redeemAddress != null,
            )
        }
        RowUniversal(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row {
                Text(
                    text = "Address:",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
                HsIconButton({}) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_content_paste_go_24),
                        contentDescription = "paste",
                    )
                }
            }
            Text(
                modifier = Modifier.weight(1f),
                text = "0x4Df4957Abc66f4778fA26D2bE7204EFc12A1dBf3",
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.MiddleEllipsis,
                maxLines = 1,
            )
            if (true) HsIconButton({
                scope.launch {
                    clipboard.setClipEntry(
                        ClipEntry(
                            ClipData.newPlainText("", "")
                        )
                    )
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_content_copy_24),
                    contentDescription = stringResource(R.string.donate_copy_cd),
                )
            }
        }
        VSpacer(8.dp)
    }
}