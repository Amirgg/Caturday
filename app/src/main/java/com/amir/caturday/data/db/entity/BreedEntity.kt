package com.amir.caturday.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amir.caturday.domain.model.Breed

@Entity(tableName = "tbl_breed")
data class BreedEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "temperament")
    val temperament: String,
    @ColumnInfo(name = "origin")
    val origin: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "life_span")
    val lifeSpan: String,
    @ColumnInfo(name = "adaptability")
    val adaptability: Int,
    @ColumnInfo(name = "affection_level")
    val affectionLevel: Int,
    @ColumnInfo(name = "energy_level")
    val energyLevel: Int,
    @ColumnInfo(name = "intelligence")
    val intelligence: Int,
    @ColumnInfo(name = "social_needs")
    val socialNeeds: Int,
    @ColumnInfo(name = "wikipedia_url")
    val wikipediaUrl: String? = null,
    @ColumnInfo(name = "reference_image_id")
    val referenceImageId: String? = null,
)

internal fun BreedEntity.toBreed(): Breed =
    Breed(
        id = id,
        name = name,
        temperament = temperament.split(", "),
        origin = origin,
        description = description,
        lifeSpan = lifeSpan,
        adaptability = adaptability,
        affectionLevel = affectionLevel,
        energyLevel = energyLevel,
        intelligence = intelligence,
        socialNeeds = socialNeeds,
        wikipediaUrl = wikipediaUrl,
        referenceImageId = referenceImageId,
        isFavorite = false,
    )
