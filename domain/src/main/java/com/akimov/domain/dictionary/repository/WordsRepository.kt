package com.akimov.domain.dictionary.repository

import com.akimov.domain.dictionary.model.WordInfoDto

interface WordsRepository {
    suspend fun searchWord(query: String): WordInfoDto

    suspend fun saveWordToDictionary(word: WordInfoDto)

    suspend fun getWordsCount(): Int
}
