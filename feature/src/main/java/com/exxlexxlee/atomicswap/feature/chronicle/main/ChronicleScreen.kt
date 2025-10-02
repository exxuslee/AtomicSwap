package com.exxlexxlee.atomicswap.feature.chronicle.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxlexxlee.atomicswap.core.common.navigation.LocalNavController
import com.exxlexxlee.atomicswap.feature.chronicle.main.models.Action
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreen(
	viewModel: ChronicleViewModel = koinViewModel(),
) {
	val viewState by viewModel.viewStates().collectAsState()
	val viewAction by viewModel.viewActions().collectAsState(null)
	val navController = LocalNavController.current

	ChronicleView(viewState) {
		viewModel.obtainEvent(it)
	}

	when (viewAction) {
		is Action.OpenSwap -> {
			viewModel.clearAction()
			navController.navigate((viewAction as Action.OpenSwap).swapId)
		}

		null -> {}
	}
}
