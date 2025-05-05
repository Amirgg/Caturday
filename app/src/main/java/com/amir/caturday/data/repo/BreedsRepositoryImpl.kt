package com.amir.caturday.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.amir.caturday.data.db.BreedDao
import com.amir.caturday.data.db.entity.BreedEntity
import com.amir.caturday.data.db.entity.toBreed
import com.amir.caturday.data.remote.ApiResponseHandler
import com.amir.caturday.data.remote.BreedsApi
import com.amir.caturday.data.remote.dto.toBreed
import com.amir.caturday.data.remote.dto.toBreedEntity
import com.amir.caturday.domain.model.Breed
import com.amir.caturday.domain.model.DataState
import com.amir.caturday.domain.model.toDomain
import com.amir.caturday.util.PreferenceKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class BreedsRepositoryImpl(
    private val breedsApi: BreedsApi,
    private val breedDao: BreedDao,
    private val dataStore: DataStore<Preferences>,
) : ApiResponseHandler(),
    BreedsRepository {
    private val favoritesFlow: Flow<Set<String>> =
        dataStore.data
            .map { prefs ->
                prefs[PreferenceKeys.FAVORITES]
                    ?.split(", ")
                    ?.filter { it.isNotBlank() }
                    ?.toSet() ?: emptySet()
            }

    override fun getBreeds(): Flow<DataState<List<Breed>>> =
        combine<List<BreedEntity>, Set<String>, DataState<List<Breed>>>(
            breedDao.getBreeds(),
            favoritesFlow,
        ) { entities, favorites ->
            val breeds =
                entities.map {
                    it.toBreed().copy(isFavorite = favorites.contains(it.id))
                }
            DataState.Success(breeds)
        }.onStart {
            emit(DataState.Loading)
        }.catch {
            emit(DataState.Failure(DataState.Failure.CODE_INVALID, it.message ?: "Something"))
        }

    override suspend fun paginate(page: Int): Flow<DataState<Unit>> =
        flow {
            emit(DataState.Loading)
            val result = call { breedsApi.getBreeds(page, LIMIT) }
            when (result) {
                is DataState.Failure -> emit(result)
                is DataState.Loading -> Unit
                is DataState.Success -> breedDao.insertAll(result.data.map { it.toBreedEntity() })
            }
        }

    override suspend fun getBreedById(id: String): Flow<DataState<Breed>> =
        flow {
            emit(DataState.Loading)
            breedDao.getBreedById(id)?.let {
                emit(DataState.Success(it.toBreed()))
            } ?: run {
                emit(DataState.Failure(DataState.Failure.CODE_NOT_FOUND, "Item not found"))
            }
        }

    override fun searchBreeds(query: String): Flow<DataState<List<Breed>>> =
        flow {
            emit(DataState.Loading)
            val result =
                call { breedsApi.searchBreeds(query = query) }
                    .toDomain { map { it.toBreed() } }
            emit(result)
        }

    override suspend fun toggleFavorite(
        id: String,
        isFavorite: Boolean,
    ) {
        val list = favoritesFlow.first().toMutableList()
        if (isFavorite) {
            list.add(id)
        } else {
            list.remove(id)
        }
        dataStore.edit { it[PreferenceKeys.FAVORITES] = list.joinToString(", ") }
    }

    override fun invalidateCache() {
        breedDao.invalidateCache()
    }

    companion object {
        const val LIMIT = 10
    }
}
