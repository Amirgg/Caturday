package com.amir.caturday.domain.usecase.breed

import com.amir.caturday.data.repo.BreedsRepository

class ToggleFavoriteUseCase(
    private val breedsRepository: BreedsRepository,
) {
    suspend operator fun invoke(
        id: String,
        isFavorite: Boolean,
    ): Unit = breedsRepository.toggleFavorite(id, isFavorite)
}
