package com.akimov.domain.training.model

import kotlinx.serialization.Serializable

@Serializable
data class WordTrainingDto(
    val id: String,
    val name: String,
    val definitions: List<String>,
)
