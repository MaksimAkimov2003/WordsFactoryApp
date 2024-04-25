package com.akimov.data.dictionary.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.akimov.data.dictionary.database.entity.DefinitionEntity
import com.akimov.data.dictionary.database.entity.WordEntity
import com.akimov.data.dictionary.database.pojo.WordWithDefinition

@Dao
interface WordsDao {
    @Upsert
    suspend fun saveWord(word: WordEntity)

    @Upsert
    suspend fun saveMeanings(meaning: List<DefinitionEntity>)

    @Transaction
    @Query("SELECT * FROM words WHERE word = :word")
    suspend fun searchWordWithDefinition(word: String): WordWithDefinition?
}