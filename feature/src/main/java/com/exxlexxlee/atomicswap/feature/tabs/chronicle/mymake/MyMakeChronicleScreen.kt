package com.exxlexxlee.atomicswap.feature.tabs.chronicle.mymake

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyMakeChronicleScreen(
    viewModel: MyMakeViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()

    MyMakeView(viewState) {
        viewModel.obtainEvent(it)
    }

}
