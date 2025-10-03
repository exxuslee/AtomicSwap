package com.exxlexxlee.atomicswap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.exxlexxlee.atomicswap.core.common.theme.AppTheme
import com.exxlexxlee.atomicswap.domain.usecases.ThemeController
import com.exxlexxlee.atomicswap.core.common.ui.rememberDoubleBackPressHandler
import com.exxlexxlee.atomicswap.feature.root.MainContent
import com.exxlexxlee.atomicswap.service.ServiceManager
import com.reown.appkit.ui.AppKitTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE_NOTIFICATIONS = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE_NOTIFICATIONS
                )
            }
        }

        enableEdgeToEdge()
        
        // Запускаем BackgroundService при старте приложения
        ServiceManager.startBackgroundService(this)
        
        setContent {
            val themeController: ThemeController = koinInject()
            val isDark by themeController.isDark.collectAsState()
            val doubleBackPressHandler = rememberDoubleBackPressHandler(this@MainActivity)

            BackHandler {
                doubleBackPressHandler.handleBackPress()
            }

            AppTheme(isDark) {
                AppKitTheme(
                    mode = if (isDark) AppKitTheme.Mode.DARK else AppKitTheme.Mode.LIGHT,
                ) {
                    MainContent()
                }
            }
        }
    }
}
