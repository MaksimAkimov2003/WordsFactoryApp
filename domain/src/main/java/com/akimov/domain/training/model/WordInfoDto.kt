package com.akimov.domain.training.model

data class WordInfoDto(
    val id: String,
    val word: String,
    val transcription: String?,
    val soundUrl: String?,
    val partOfSpeech: String?,
    val knowledgeCoefficient: Int,
)