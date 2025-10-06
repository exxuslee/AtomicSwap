package com.exxlexxlee.atomicswap.feature.tabs.settings.scanner

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.exxlexxlee.atomicswap.core.common.ui.ListErrorView
import com.exxlexxlee.atomicswap.core.common.ui.ListLoadingView
import com.exxlexxlee.atomicswap.feature.R
import com.exxlexxlee.atomicswap.feature.tabs.settings.scanner.models.Action
import com.exxlexxlee.atomicswap.feature.tabs.settings.scanner.models.Event
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    viewModel.obtainEvent(
        Event.PermissionState(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.clearAction()
        viewModel.obtainEvent(Event.PermissionState(granted))
    }


    when (viewAction) {
        Action.RequestPermission -> {
            LaunchedEffect(viewAction) {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        null -> {}
    }

    when (viewState.hasCameraPermission) {
        true -> {
            ScannerView()
        }

        false -> {
            ListErrorView(
                errorText = "Camera permission is required to scan QR codes.",
                icon = R.drawable.outline_lock_open_circle_24
            ) {
                viewModel.obtainEvent(Event.RequestPermission)
            }
        }

        null -> {
            ListLoadingView("Checking permissions...")
        }
    }
}
