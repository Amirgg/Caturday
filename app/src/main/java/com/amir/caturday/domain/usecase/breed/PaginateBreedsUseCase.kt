package com.amir.caturday.domain.usecase.breed

import com.amir.caturday.data.repo.BreedsRepository
import com.amir.caturday.domain.model.DataState
import kotlinx.coroutines.flow.Flow

class PaginateBreedsUseCase(
    private val breedsRepository: BreedsRepository,
) {
    suspend operator fun invoke(page: Int): Flow<DataState<Boolean>> = breedsRepository.paginate(page)
}
