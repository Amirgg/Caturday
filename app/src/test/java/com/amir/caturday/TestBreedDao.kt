package com.amir.caturday

import com.amir.caturday.data.db.BreedDao
import com.amir.caturday.data.db.entity.BreedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestBreedDao : BreedDao {
    val data = mutableListOf<BreedEntity>()

    override fun insertAll(breeds: List<BreedEntity>) {
        data.addAll(breeds)
    }

    override fun getBreeds(): Flow<List<BreedEntity>> =
        flow {
            emit(data)
        }

    override fun getBreedById(id: String): Flow<BreedEntity> =
        flow {
            emit(data.first { it.id == id })
        }

    override fun invalidateCache() {
        data.clear()
    }
}
