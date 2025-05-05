package com.amir.caturday.domain.usecase

import com.amir.caturday.data.repo.BreedsRepository
import com.amir.caturday.domain.model.Breed
import com.amir.caturday.domain.model.DataState
import kotlinx.coroutines.flow.Flow

class GetBreedsUseCase(
    private val breedsRepository: BreedsRepository,
) {
    operator fun invoke(): Flow<DataState<List<Breed>>> = breedsRepository.getBreeds()
}
