package com.akimov.domain.dictionary.repository

import com.akimov.domain.dictionary.model.WordInfoDto
import com.akimov.domain.training.model.WordTrainingDto

interface WordsRepository {
    suspend fun searchWord(query: String): WordInfoDto

    suspend fun saveWordToDictionary(word: WordInfoDto)

    suspend fun getWordsCount(): Int

    suspend fun getSortedByCoefficientWords(limit: Int): List<WordTrainingDto>

    suspend fun getRandomWords(
        excludedName: String,
        limit: Int
    ): List<String>

    fun getMockedVariants(): List<String>
}
