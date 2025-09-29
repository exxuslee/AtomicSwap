package com.example.atomicswap.feature.settings.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atomicswap.core.common.theme.AppTheme
import com.example.atomicswap.core.common.ui.HeaderStick
import com.example.atomicswap.core.common.ui.TopAppBar
import com.example.atomicswap.feature.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutView(onBack: () -> Unit) {
    Column {
        val title = stringResource(R.string.about, stringResource(R.string.app_name))
        TopAppBar(title, onBack)

        val sections = remember {
            listOf(
                "Application" to "AtomicSwap helps you manage and swap crypto assets with a simple, secure interface.",
                "Version" to "1.0.0", // consider wiring from BuildConfig if desired
                "Privacy" to "We do not collect personal data. Network calls are limited to required APIs.",
            )
        }

        val uriHandler = LocalUriHandler.current

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            sections.forEach { (title, body) ->
                stickyHeader {
                    HeaderStick(title)
                }

                item {
                    Text(
                        text = body,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            stickyHeader {
                HeaderStick("Links")
            }

            item {
                Button(
                    onClick = { uriHandler.openUri("https://github.com/") },
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Text("GitHub")
                }
            }

            item {
                Button(
                    onClick = { uriHandler.openUri("https://t.me/") },
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    Text("Telegram")
                }
            }

            item {
                Text(
                    text = "© 2025 AtomicSwap",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun AboutView_Preview() {
    AppTheme {
        AboutView(
            onBack = {}
        )
    }
}