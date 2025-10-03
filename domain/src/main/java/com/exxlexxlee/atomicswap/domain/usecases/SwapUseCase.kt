package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.model.Swap
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SwapUseCase {
    val swapsFlow: Flow<List<Swap>>

    fun swap(swapId: String): Swap?

    fun swaps(filter: FilterStateChronicle): Flow<List<Swap>>

    fun badgeType(): Int?

    class Base(
        private val swapRepository: SwapRepository,
    ) : SwapUseCase {

        override val swapsFlow: Flow<List<Swap>> = swapRepository.swaps

        override fun swap(swapId: String): Swap? =
            swapRepository.swap(swapId)

        override fun swaps(filter: FilterStateChronicle): Flow<List<Swap>> =
            swapRepository.swaps.map { list ->
                list.filter { it.swapState in filter.relatedStates }
            }

        override fun badgeType(): Int = 3
    }
}