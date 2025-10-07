package com.exxlexxlee.atomicswap.data.repository

import com.exxlexxlee.atomicswap.core.database.MakeDao
import com.exxlexxlee.atomicswap.core.database.model.MakeEntity
import com.exxlexxlee.atomicswap.core.swap.model.Make
import com.exxlexxlee.atomicswap.data.mapper.toDomain
import com.exxlexxlee.atomicswap.domain.repository.MakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MakeRepositoryImpl(
    private val makeDao: MakeDao,
) : MakeRepository {

    override val makes: Flow<List<Make>> = makeDao.makeAll()
        .map { list -> list.map(MakeEntity::toDomain) }

    override fun make(makeId: String) = makeDao.make(makeId).toDomain()

    override fun myMake(makerId: String): List<Make> = makeDao.myMake(makerId).map { it.toDomain() }


}
