package com.akimov.domain.dictionary.repository

import com.akimov.domain.dictionary.model.WordWithMeaningsDto
import com.akimov.domain.training.model.WordInfoDto
import com.akimov.domain.training.model.WordTrainingDto
import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    suspend fun searchWord(query: String): WordWithMeaningsDto

    suspend fun saveWordToDictionary(word: WordWithMeaningsDto)

    suspend fun getWordById(wordId: String): WordInfoDto

    suspend fun getWordsCount(): Int

    fun getWordsCountFlow(): Flow<Int>

    suspend fun getWordsCount(name: String): Int

    fun getStudiedWordsCountFlow(): Flow<Int>

    suspend fun getSortedByCoefficientWords(limit: Int): List<WordTrainingDto>

    suspend fun getRandomWords(
        excludedName: String,
        limit: Int
    ): List<String>

    fun getMockedVariants(): List<String>

    suspend fun updateWord(new: WordInfoDto)
}
