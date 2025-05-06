package com.amir.caturday.data

import com.amir.caturday.data.remote.dto.BreedDto

internal val breedDto =
    BreedDto(
        id = "1",
        name = "Catty",
        temperament = "Sweet, Affectionate, Loyal, Playful, Cute",
        origin = "Burma",
        description = "description",
        lifeSpan = "lifeSpan",
        adaptability = 0,
        affectionLevel = 1,
        energyLevel = 2,
        intelligence = 3,
        socialNeeds = 5,
        wikipediaUrl = "https://en.wikipedia.org/wiki/Havana_Brown",
        referenceImageId = "njK25knLH",
    )
internal val breedDtoList =
    buildList {
        repeat(10) {
            add(breedDto.copy(id = it.toString()))
        }
    }
