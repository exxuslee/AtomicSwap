package com.exxlexxlee.atomicswap.feature.chronicle.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.Action
import com.exxlexxlee.atomicswap.feature.navigation.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
	viewModel: ChronicleViewModel = koinViewModel(),
) {
	val viewState by viewModel.viewStates().collectAsState()
	val viewAction by viewModel.viewActions().collectAsState(null)

	ChronicleView(viewState) {
		viewModel.obtainEvent(it)
	}

}
