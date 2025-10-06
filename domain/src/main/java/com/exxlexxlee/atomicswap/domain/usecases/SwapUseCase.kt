package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SwapUseCase {
    val swapsFlow: Flow<List<Swap>>

    fun swap(swapId: String): Swap?

    fun swaps(filter: FilterStateChronicle): Flow<List<Swap>>

    val swapFilterBadgeType: Flow<Map<FilterStateChronicle, Int?>>
    fun filterBadgeType(): Map<FilterStateChronicle, Int?>
    val mainBadgeType: Flow<Int?>
    fun mainBadgeType(): Int?

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

        override val swapFilterBadgeType: Flow<Map<FilterStateChronicle, Int?>> =
            swapRepository.swaps.map { filterBadgeType() }

        override fun filterBadgeType(): Map<FilterStateChronicle, Int?> {
            val all = swapRepository.swapsAll()
            val myMake =
                all.filter { it.swapState in FilterStateChronicle.MyMake.relatedStates }.size
            val active =
                all.filter { it.swapState in FilterStateChronicle.Active.relatedStates }.size
            val complete =
                all.filter { it.swapState in FilterStateChronicle.Complete.relatedStates }.size
            val refund =
                all.filter { it.swapState in FilterStateChronicle.Refund.relatedStates }.size

            return mapOf(
                FilterStateChronicle.MyMake to myMake,
                FilterStateChronicle.Active to active,
                FilterStateChronicle.Complete to complete,
                FilterStateChronicle.Refund to refund,
            )
        }

        override val mainBadgeType: Flow<Int?> = swapRepository.swaps.map { mainBadgeType() }
        override fun mainBadgeType(): Int? {
            return swapRepository.swapsAll()
                .filter { it.swapState in FilterStateChronicle.Active.relatedStates }.size
        }


    }
}