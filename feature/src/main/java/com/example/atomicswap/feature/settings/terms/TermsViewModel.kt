package com.example.atomicswap.feature.settings.terms

import com.example.atomicswap.core.ui.base.BaseViewModel
import com.example.atomicswap.feature.settings.main.models.SettingsAction
import com.example.atomicswap.feature.settings.main.models.SettingsEvent
import com.example.atomicswap.feature.settings.main.models.SettingsViewState
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import java.security.MessageDigest
import kotlin.random.Random
import androidx.core.graphics.createBitmap
import com.example.atomicswap.domain.usecases.ThemeController
import com.example.atomicswap.feature.settings.terms.models.Action
import com.example.atomicswap.feature.settings.terms.models.Event
import com.example.atomicswap.feature.settings.terms.models.ViewState
import java.nio.ByteBuffer

class TermsViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }

    }

}