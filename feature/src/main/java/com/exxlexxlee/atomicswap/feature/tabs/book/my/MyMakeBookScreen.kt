package com.exxlexxlee.atomicswap.feature.tabs.book.my

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyMakeBookScreen(
    viewModel: MyMakeViewModel = koinViewModel(),
) {
	val viewState by viewModel.viewStates().collectAsState()
	val viewAction by viewModel.viewActions().collectAsState(null)

    MyMakeView(viewState) {
        viewModel.obtainEvent(it)
    }

}
