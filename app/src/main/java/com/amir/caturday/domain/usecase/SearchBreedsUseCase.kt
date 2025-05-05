package com.amir.caturday.domain.usecase

import com.amir.caturday.data.repo.BreedsRepository
import com.amir.caturday.domain.model.Breed
import com.amir.caturday.domain.model.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class SearchBreedsUseCase(
    private val breedsRepository: BreedsRepository,
) {
    operator fun invoke(query: String): Flow<DataState<List<Breed>>> =
        flow {
            if (query.isBlank()) {
                emit(DataState.Failure(DataState.Failure.CODE_INVALID, "Query must not be empty."))
                return@flow
            }
            emitAll(breedsRepository.searchBreeds(query.trim()))
        }.catch {
            emit(DataState.Failure(DataState.Failure.CODE_INVALID, it.message ?: "Unknown error."))
        }
}
