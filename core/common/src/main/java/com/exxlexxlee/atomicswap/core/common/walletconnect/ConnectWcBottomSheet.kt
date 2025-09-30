package com.exxlexxlee.atomicswap.core.common.walletconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.reown.appkit.ui.components.internal.AppKitComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectWcBottomSheet(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    closeModal: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        AppKitComponent(
            shouldOpenChooseNetwork = true,
            closeModal = closeModal
        )
    }
}
