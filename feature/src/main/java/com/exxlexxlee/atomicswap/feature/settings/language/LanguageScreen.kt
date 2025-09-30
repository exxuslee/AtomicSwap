package com.exxlexxlee.atomicswap.feature.settings.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.feature.settings.language.models.Action
import com.hwasfy.localize.api.LanguageManager
import org.koin.androidx.compose.koinViewModel

@Composable
fun LanguageScreen(
    viewModel: LanguageViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    val navController = LocalNavController.current

    LanguageView(viewState) {
        viewModel.obtainEvent(it)
    }

    when (viewAction) {
        is Action.SetLocale -> {
            LanguageManager.setLanguage(
                LocalContext.current,
                (viewAction as Action.SetLocale).locale
            )
            viewModel.clearAction()
            navController.popBackStack()
        }
        is Action.PopBackStack -> {
            viewModel.clearAction()
            navController.popBackStack()
        }
        null -> {}
    }
}