package com.akimov.data.words.model

data class WordModel(
    val word: String,
    val phonetic: String?,
    val phonetics: List<PhoneticModel>,
    val meanings: List<MeaningModel>
)
