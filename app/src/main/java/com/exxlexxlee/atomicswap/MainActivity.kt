package com.exxlexxlee.atomicswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.domain.usecases.ThemeController
import com.exxlexxlee.atomicswap.core.common.ui.rememberDoubleBackPressHandler
import com.exxlexxlee.atomicswap.feature.root.MainContent
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val themeController: ThemeController = koinInject()
            val isDark by themeController.isDark.collectAsState()
            val doubleBackPressHandler = rememberDoubleBackPressHandler(this@MainActivity)

            BackHandler {
                doubleBackPressHandler.handleBackPress()
            }

            AppTheme(isDark) {
                MainContent()
            }
        }
    }
}
