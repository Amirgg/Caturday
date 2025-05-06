package com.amir.caturday.ui.details.model

import androidx.annotation.IntRange
import androidx.annotation.StringRes

data class BreedAttributeUiModel(
    @StringRes
    val title: Int,
    @IntRange(from = 0, to = 5)
    val level: Int,
)
