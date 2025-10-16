package com.exxlexxlee.atomicswap.feature.common.tokens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.exxlexxlee.atomicswap.core.common.ui.CellUniversalSection
import com.exxlexxlee.atomicswap.core.common.ui.HsRow
import com.exxlexxlee.atomicswap.core.swap.model.Blockchain
import com.exxlexxlee.atomicswap.feature.common.tokens.models.Event
import com.exxlexxlee.atomicswap.feature.common.tokens.models.ViewState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ChainsView(
    viewState: ViewState,
    eventHandler: (Event) -> Unit,
) {
    CellUniversalSection(
        Blockchain.supportedList().map { chain ->
            {
                HsRow(
                    imageUrl = chain.iconUrl,
                    titleContent = {
                        Text(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            text = chain.label,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    onClick = {
                        eventHandler.invoke(Event.ChainsCheck(chain))
                    },
                    arrowRight = false,
                ) {
                    Checkbox(
                        checked = !(viewState.isChainDismiss[chain]?:false),
                        onCheckedChange = {
                            eventHandler.invoke(Event.ChainsCheck(chain))
                        }
                    )
                }
            }
        }
    )


}