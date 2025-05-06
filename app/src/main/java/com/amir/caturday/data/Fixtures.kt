package com.amir.caturday.data

import com.amir.caturday.data.remote.dto.BreedDto

internal val breedDto =
    BreedDto(
        id = "1",
        name = "Catty",
        temperament = "Sweet, Affectionate, Loyal, Playful, Cute, Sensitive, Lively, Intelligent, Wild",
        origin = "Burma",
        description =
            "American Bobtails are loving and incredibly intelligent cats possessing a distinctive wild appearance. " +
                "They are extremely interactive cats that bond with their human family with great devotion.",
        lifeSpan = "11 - 15",
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
