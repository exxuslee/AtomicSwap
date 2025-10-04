package com.exxlexxlee.atomicswap.core.walletconnect.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.reown.appkit.ui.components.internal.AppKitComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectWcBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        AppKitComponent(
            shouldOpenChooseNetwork = true,
            closeModal = onDismissRequest
        )
    }
}
