package com.example.atomicswap.feature.settings.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.atomicswap.feature.settings.language.models.Action
import com.hwasfy.localize.api.LanguageManager
import com.hwasfy.localize.util.SupportedLocales
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    LanguageView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {
        is Action.SetLocale -> {
            viewModel.clearAction()
            LanguageManager.setLanguage(
                LocalContext.current,
                (viewAction as Action.SetLocale).locale
            )
            viewModel.refresh()
        }
        null -> {}
    }
}