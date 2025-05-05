package com.amir.caturday.domain.usecase

import com.amir.caturday.data.repo.BreedsRepository
import com.amir.caturday.domain.model.Breed
import com.amir.caturday.domain.model.DataState
import kotlinx.coroutines.flow.Flow

class GetBreedByIdUseCase(
    private val breedsRepository: BreedsRepository,
) {
    suspend operator fun invoke(id: String): Flow<DataState<Breed>> = breedsRepository.getBreedById(id)
}
