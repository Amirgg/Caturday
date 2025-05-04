package com.amir.caturday.domain.model

import androidx.annotation.IntRange

data class Breed(
    val id: String,
    val name: String,
    val temperament: List<String>,
    val origin: String,
    val description: String,
    val lifeSpan: String,
    @IntRange(from = 0, to = 5)
    val adaptability: Int,
    @IntRange(from = 0, to = 5)
    val affectionLevel: Int,
    @IntRange(from = 0, to = 5)
    val energyLevel: Int,
    @IntRange(from = 0, to = 5)
    val intelligence: Int,
    @IntRange(from = 0, to = 5)
    val socialLeeds: Int,
    val wikipediaUrl: String,
    val referenceImageId: String,
)
