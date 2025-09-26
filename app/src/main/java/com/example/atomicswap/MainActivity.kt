package com.example.atomicswap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.atomicswap.domain.usecases.ThemeController
import com.example.atomicswap.domain.usecases.LanguageController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.atomicswap.core.ui.theme.AppTheme
import org.koin.compose.koinInject
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val themeController: ThemeController = koinInject()
            val languageController: LanguageController = koinInject()
            val isDark by themeController.isDark.collectAsState()
            val languageTag = languageController.languageTag.collectAsState()

            val locales = LocaleListCompat.forLanguageTags(languageTag.value)
            AppCompatDelegate.setApplicationLocales(locales)

            AppTheme(isDark) {
                MainContent()
            }
        }
    }
}
