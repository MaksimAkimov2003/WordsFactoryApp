package com.akimov.data.words.model

data class MeaningModel(
    val partOfSpeech: String?,
    val definitions: List<DefinitionModel>,
)
