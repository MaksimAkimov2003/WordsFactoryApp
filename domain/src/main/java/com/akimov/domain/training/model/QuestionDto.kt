package com.akimov.domain.training.model

import kotlinx.collections.immutable.ImmutableList

data class QuestionDto(
    val tittle: String,
    val variants: ImmutableList<String>,
    val correctVariantIndex: Int,
)
