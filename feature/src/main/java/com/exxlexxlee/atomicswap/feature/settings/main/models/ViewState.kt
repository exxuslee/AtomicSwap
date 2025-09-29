package com.exxlexxlee.atomicswap.feature.settings.main.models

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap

data class ViewState(
    val title: String = "",
    val isDark: Boolean = false,
    val isWalletConnect: Boolean = false,
    val unreadCount: Int = 0,
    val avatar: Bitmap = createBitmap(360, 360),
    val showClearStorageConfirm: Boolean = false,
)