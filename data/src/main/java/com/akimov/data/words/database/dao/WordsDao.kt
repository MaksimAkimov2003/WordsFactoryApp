package com.akimov.data.words.database.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.akimov.data.words.database.entity.DefinitionEntity
import com.akimov.data.words.database.entity.WordEntity
import com.akimov.data.words.database.pojo.WordWithDefinition

@Dao
interface WordsDao {
    @Upsert
    suspend fun saveWord(word: WordEntity)

    @Upsert
    suspend fun saveMeanings(meaning: List<DefinitionEntity>)

    @Query("SELECT COUNT(*) FROM words")
    suspend fun getCount(): Int

    @Transaction
    @Query("SELECT * FROM words WHERE word = :word")
    suspend fun searchWordWithDefinition(word: String): WordWithDefinition?

    @Transaction
    @Query("SELECT * FROM words ORDER BY knowledgeCoefficient ASC LIMIT :limit")
    suspend fun getWordsSortedByCoefficient(limit: Int): List<WordWithDefinition>

    @Query("SELECT * FROM words WHERE word != :excludedName ORDER BY RANDOM() LIMIT :limit")
    suspend fun getTwoRandomWordsExcludingByName(
        excludedName: String,
        limit: Int
    ): List<WordEntity>
}
