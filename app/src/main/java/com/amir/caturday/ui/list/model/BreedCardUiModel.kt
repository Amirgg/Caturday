package com.amir.caturday.ui.list.model

import com.amir.caturday.domain.model.Breed
import com.amir.caturday.util.Constant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class BreedCardUiModel(
    val id: String,
    val imageUrl: String?,
    val name: String,
    val origin: String,
    val temperaments: ImmutableList<String>,
    val isFavorite: Boolean,
)

internal fun Breed.toBreedCardUiModel(): BreedCardUiModel =
    BreedCardUiModel(
        id = id,
        imageUrl = "${Constant.CON_IMAGE_BASE_URL}$referenceImageId.jpg",
        name = name,
        origin = origin,
        temperaments = temperament.toImmutableList(),
        isFavorite = isFavorite,
    )
