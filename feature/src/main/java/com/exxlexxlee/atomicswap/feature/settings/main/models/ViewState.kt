package com.exxlexxlee.atomicswap.feature.settings.main.models

import android.graphics.Bitmap
import androidx.core.graphics.createBitmap
import com.exxlexxlee.atomicswap.domain.model.SupportedAggregators
import com.hwasfy.localize.util.SupportedLocales

data class ViewState(
    val isDark: Boolean = false,
    val isWalletConnect: Boolean = false,
    val isEmptyLocalStorage: Boolean = true,
    val isTermsOfUseRead: Boolean,
    val unreadCount: Int = 0,
    val avatar: Bitmap = createBitmap(360, 360),
    val showClearStorageConfirm: Boolean = false,
    val priceAggregator: SupportedAggregators = SupportedAggregators.COIN_MARKET_CAP,
)