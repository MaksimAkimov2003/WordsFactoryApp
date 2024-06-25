package com.akimov.domain.dictionary.model

import kotlinx.collections.immutable.ImmutableList

data class WordWithMeaningsDto(
    val word: String,
    val transcription: String?,
    val soundUrl: String?,
    val partOfSpeech: String?,
    val meanings: ImmutableList<MeaningDto>,
    val knowledgeCoefficient: Int = 0
)
