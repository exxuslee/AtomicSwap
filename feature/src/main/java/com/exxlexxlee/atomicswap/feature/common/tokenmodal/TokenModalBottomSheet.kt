package com.exxlexxlee.atomicswap.feature.common.tokenmodal

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokenModalBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onClick: (String) -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        Text("TokenModalBottomSheet")
    }
}