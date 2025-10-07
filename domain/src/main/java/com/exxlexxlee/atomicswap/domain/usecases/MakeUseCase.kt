package com.exxlexxlee.atomicswap.domain.usecases

import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.core.swap.model.Swap
import com.exxlexxlee.atomicswap.domain.model.FilterStateChronicle
import com.exxlexxlee.atomicswap.domain.repository.MakeRepository
import com.exxlexxlee.atomicswap.domain.repository.SwapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface MakeUseCase {
    val makesFlow: Flow<List<Make>>
    fun make(makeId: String): Make
    fun myMake(makerId: String): List<Make>


    class Base(
        private val makeRepository: MakeRepository,
    ) : MakeUseCase {

        override val makesFlow: Flow<List<Make>> = makeRepository.makes

        override fun make(makeId: String): Make = makeRepository.make(makeId)

        override fun myMake(makerId: String) = makeRepository.myMake(makerId)


    }
}