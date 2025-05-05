package com.amir.caturday.domain.usecase.breed

import com.amir.caturday.data.repo.BreedsRepository

class InvalidateCacheUseCase(
    private val breedsRepository: BreedsRepository,
) {
    operator fun invoke() = breedsRepository.invalidateCache()
}
