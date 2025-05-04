package com.amir.caturday.data.dto

import com.amir.caturday.domain.model.Breed
import com.amir.caturday.util.Constant
import kotlinx.serialization.SerialName

data class BreedDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("temperament")
    val temperament: String,
    @SerialName("origin")
    val origin: String,
    @SerialName("description")
    val description: String,
    @SerialName("life_span")
    val lifeSpan: String,
    @SerialName("adaptability")
    val adaptability: Int,
    @SerialName("affection_level")
    val affectionLevel: Int,
    @SerialName("energy_level")
    val energyLevel: Int,
    @SerialName("intelligence")
    val intelligence: Int,
    @SerialName("social_needs")
    val socialLeeds: Int,
    @SerialName("wikipedia_url")
    val wikipediaUrl: String,
    @SerialName("reference_image_id")
    val referenceImageId: String,
)

internal fun BreedDto.toBreed(): Breed =
    Breed(
        id = id,
        name = name,
        temperament = temperament.split(", "),
        origin = origin,
        description = description,
        lifeSpan = lifeSpan,
        adaptability = adaptability.coerceIn(0, 5),
        affectionLevel = affectionLevel.coerceIn(0, 5),
        energyLevel = energyLevel.coerceIn(0, 5),
        intelligence = intelligence.coerceIn(0, 5),
        socialLeeds = socialLeeds.coerceIn(0, 5),
        wikipediaUrl = wikipediaUrl,
        referenceImageId = "${Constant.CON_IMAGE_BASE_URL}$referenceImageId.jpg",
    )
