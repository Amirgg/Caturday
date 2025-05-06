package com.amir.caturday.ui.details.model

import com.amir.caturday.R
import com.amir.caturday.domain.model.Breed
import com.amir.caturday.util.Constant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

data class BreedDetailsUiModel(
    val id: String,
    val name: String,
    val temperament: ImmutableList<String>,
    val description: String,
    val specs: ImmutableList<SpecRowUiModel>,
    val attributes: ImmutableList<BreedAttributeUiModel>,
    val wikipediaUrl: String?,
    val imageUrl: String?,
    val isFavorite: Boolean,
)

internal fun Breed.toBreedDetailsUiModel(): BreedDetailsUiModel =
    BreedDetailsUiModel(
        id = id,
        name = name,
        temperament = temperament.toImmutableList(),
        description = description,
        specs =
            persistentListOf(
                SpecRowUiModel(R.string.origin, origin),
                SpecRowUiModel(R.string.life_span, lifeSpan),
            ),
        attributes =
            persistentListOf(
                BreedAttributeUiModel(R.string.adaptability, adaptability),
                BreedAttributeUiModel(R.string.affectionLevel, affectionLevel),
                BreedAttributeUiModel(R.string.energyLevel, energyLevel),
                BreedAttributeUiModel(R.string.intelligence, intelligence),
                BreedAttributeUiModel(R.string.socialNeeds, socialNeeds),
            ),
        wikipediaUrl = wikipediaUrl,
        imageUrl = "${Constant.CON_IMAGE_BASE_URL}$referenceImageId.jpg",
        isFavorite = isFavorite,
    )
