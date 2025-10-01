package com.exxlexxlee.atomicswap.feature.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import com.exxlexxlee.atomicswap.feature.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController

@Composable
fun HistoryScreen() {
	val navController = LocalNavController.current

	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(stringResource(R.string.title_chronicle))
	}
}
