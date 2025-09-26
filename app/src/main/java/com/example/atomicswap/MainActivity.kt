package com.example.atomicswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.atomicswap.core.ui.theme.AppTheme
import com.example.atomicswap.domain.usecases.ThemeController
import com.example.atomicswap.utils.rememberDoubleBackPressHandler
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
