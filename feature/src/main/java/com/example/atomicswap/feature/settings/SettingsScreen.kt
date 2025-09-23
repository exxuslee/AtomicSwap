package com.example.atomicswap.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.res.stringResource
import com.example.atomicswap.feature.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
fun SettingsScreen() {
	val themeController: ThemeController = koinInject()
	val isDark = themeController.isDark.collectAsState().value

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(stringResource(R.string.title_settings))
		Switch(
			checked = isDark,
			onCheckedChange = { themeController.setDark(it) },
			colors = SwitchDefaults.colors()
		)
	}
}
