package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.repository.SettingsRepository
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface SwapUseCase {
    val swapsFlow: Flow<List<Swap>>
    fun swap(swapId: String): Swap



    class Base(
        private val swapRepository: SwapRepository,
    ) : SwapUseCase {
        override val swapsFlow = swapRepository.swaps

        override fun swap(swapId: String) = swapRepository.swap(swapId)
    }
}