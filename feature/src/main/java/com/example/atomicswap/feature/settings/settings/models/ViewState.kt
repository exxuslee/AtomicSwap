package com.example.atomicswap.feature.settings.settings.models

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap

data class ViewState(
    val title: String = "",
    val isDark: Boolean = false,
    val isWalletConnect: Boolean = false,
    val avatar: Bitmap = createBitmap(360, 360),
)