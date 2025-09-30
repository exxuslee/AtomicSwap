package com.exxlexxlee.atomicswap.feature.settings.main

import com.exxlexxlee.atomicswap.core.common.base.BaseViewModel
import com.exxlexxlee.atomicswap.feature.settings.main.models.Action
import com.exxlexxlee.atomicswap.feature.settings.main.models.Event
import com.exxlexxlee.atomicswap.feature.settings.main.models.ViewState
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import java.security.MessageDigest
import kotlin.random.Random
import androidx.core.graphics.createBitmap
import androidx.lifecycle.viewModelScope
import com.exxlexxlee.atomicswap.domain.usecases.NotificationReaderUseCase
import com.exxlexxlee.atomicswap.core.common.walletconnect.WalletConnectManager
import com.exxlexxlee.atomicswap.domain.usecases.ThemeController
import kotlinx.coroutines.launch
import java.nio.ByteBuffer

class SettingsViewModel(
    private val themeController: ThemeController,
    private val notificationReaderUseCase: NotificationReaderUseCase,
    private val walletConnectManager: WalletConnectManager,
) : BaseViewModel<ViewState, Action,
        Event>(initialState = ViewState()) {

    init {
        viewState = viewState.copy(
            avatar = generateIdenticonBitmap("0", 360),
            isDark = themeController.isDark.value
        )

        viewModelScope.launch {
            notificationReaderUseCase.unreadCount.collect {
                viewState = viewState.copy(unreadCount = it)
            }
        }
        viewModelScope.launch {
            walletConnectManager.delegate.connectionState.collect {
                viewState = viewState.copy(isWalletConnect = it.isAvailable)
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.IsDark -> {
                themeController.setDark(viewEvent.newValue)
                viewState = viewState.copy(isDark = viewEvent.newValue)
            }

            Event.OpenWalletConnectDialog -> viewAction = Action.ConnectWcDialog
            Event.OpenTermsScreen -> viewAction = Action.OpenTermsScreen
            Event.OpenLanguageScreen -> viewAction = Action.OpenLanguageScreen
            Event.OpenNotificationScreen -> viewAction = Action.OpenNotificationScreen
            Event.OpenAboutScreen -> viewAction = Action.OpenAboutScreen
            Event.OpenDonateScreen -> viewAction = Action.OpenDonateScreen
            Event.OpenClearStorage -> viewAction = Action.LocaleStorageDialog
            Event.ConfirmClearStorage -> {
                viewModelScope.launch {
                    notificationReaderUseCase.deleteAll()
                    clearAction()
                }
            }
        }

    }

    private fun randomFromSeed(seed: String): Random {
        val md = MessageDigest.getInstance("SHA-256")
        val hash = md.digest(seed.toByteArray(Charsets.UTF_8))
        val bb = ByteBuffer.wrap(hash, 0, 8)
        return Random(bb.long)
    }

    fun generateIdenticonBitmap(seed: String, sizePx: Int, grid: Int = 5): Bitmap {
        require(grid >= 3) { "grid must be >= 3" }
        val rnd = randomFromSeed(seed)
        val bgColor = 0xFFFFFFFF.toInt()

        fun hsvToColor(h: Int, s: Float, v: Float): Int {
            val hf = (h % 360) / 60f
            val i = hf.toInt()
            val f = hf - i
            val p = v * (1 - s)
            val q = v * (1 - s * f)
            val t = v * (1 - s * (1 - f))
            val (r, g, b) = when (i) {
                0 -> Triple(v, t, p)
                1 -> Triple(q, v, p)
                2 -> Triple(p, v, t)
                3 -> Triple(p, q, v)
                4 -> Triple(t, p, v)
                else -> Triple(v, p, q)
            }
            val ri = (r * 255).toInt()
            val gi = (g * 255).toInt()
            val bi = (b * 255).toInt()
            return (0xFF shl 24) or (ri shl 16) or (gi shl 8) or bi
        }

        val colors = List(3) {
            val hue = rnd.nextInt(0, 360)
            val sat = 0.6f + rnd.nextFloat() * 0.4f
            val value = 0.5f + rnd.nextFloat() * 0.5f
            hsvToColor(hue, sat, value)
        }

        val bmp = createBitmap(sizePx, sizePx)
        val canvas = Canvas(bmp)
        canvas.drawColor(bgColor)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }

        val cellSize = sizePx / grid.toFloat()
        val padding = (cellSize * 0.08f)
        val innerCell = cellSize - padding * 2

        val colsLeft = grid / 2 + (if (grid % 2 == 1) 1 else 0)
        val filled = Array(grid) { IntArray(colsLeft) } // храним индекс цвета (0..2), -1 если пусто

        for (r in 0 until grid) {
            for (c in 0 until colsLeft) {
                if (rnd.nextFloat() < 0.45f) {
                    filled[r][c] = rnd.nextInt(0, colors.size) // случайный выбор из 3-х
                } else {
                    filled[r][c] = -1 // пустая клетка
                }
            }
        }

        for (r in 0 until grid) {
            for (c in 0 until grid) {
                val srcCol = if (c < colsLeft) c else grid - 1 - c
                val colorIndex = filled[r][srcCol]
                if (colorIndex >= 0) {
                    paint.color = colors[colorIndex]
                    val left = c * cellSize + padding
                    val top = r * cellSize + padding
                    val rect = RectF(left, top, left + innerCell, top + innerCell)
                    canvas.drawRoundRect(rect, innerCell * 0.12f, innerCell * 0.12f, paint)
                }
            }
        }

        return bmp
    }

}