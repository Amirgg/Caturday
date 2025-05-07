package com.amir.caturday.data.repo

import com.amir.caturday.domain.model.Breed
import com.amir.caturday.domain.model.DataState
import kotlinx.coroutines.flow.Flow

interface BreedsRepository {
    fun getBreeds(): Flow<DataState<List<Breed>>>

    suspend fun paginate(page: Int): Flow<DataState<Boolean>>

    suspend fun getBreedById(id: String): Flow<DataState<Breed>>

    suspend fun toggleFavorite(
        id: String,
        isFavorite: Boolean,
    )

    fun invalidateCache()
}
