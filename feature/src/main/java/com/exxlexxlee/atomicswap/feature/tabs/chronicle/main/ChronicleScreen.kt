package com.exxlexxlee.atomicswap.feature.tabs.chronicle.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChronicleScreen(
	viewModel: ChronicleViewModel = koinViewModel(),
) {
	val viewState by viewModel.viewStates().collectAsState()
	val viewAction by viewModel.viewActions().collectAsState(null)

	ChronicleView(viewState) {
		viewModel.obtainEvent(it)
	}

}
