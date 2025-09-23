package com.example.atomicswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.atomicswap.core.ui.theme.AtomicComposeTheme
import com.example.atomicswap.feature.settings.ThemeController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val themeController: ThemeController = koinInject()
            val isDark by themeController.isDark.collectAsState()

            AtomicComposeTheme(isDark) {
                MainContent()
            }
        }
    }
}
