package com.exxlexxlee.atomicswap.feature.tabs.book

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookScreen(
	viewModel: BookViewModel = koinViewModel(),
) {
	val viewState by viewModel.viewStates().collectAsState()

	BookView(viewState) {
		viewModel.obtainEvent(it)
	}
}
