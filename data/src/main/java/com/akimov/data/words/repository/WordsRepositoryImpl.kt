package com.akimov.data.words.repository

import com.akimov.data.words.api.WordsService
import com.akimov.data.words.database.dao.WordsDao
import com.akimov.data.words.database.pojo.WordWithDefinition
import com.akimov.data.words.mapper.toDto
import com.akimov.data.words.mapper.toEntity
import com.akimov.data.words.mapper.toTraining
import com.akimov.domain.dictionary.model.WordWithMeaningsDto
import com.akimov.domain.dictionary.repository.WordsRepository
import com.akimov.domain.training.model.WordInfoDto
import com.akimov.domain.training.model.WordTrainingDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class WordsRepositoryImpl(
    private val api: WordsService,
    private val dao: WordsDao,
) : WordsRepository {
    companion object {
        val MOCKED_VARIANTS = listOf(
            "Smiling",
            "Freezing"
        )
    }

    override suspend fun searchWord(query: String): WordWithMeaningsDto {
        return try {
            api.searchWord(query).toDto()
        } catch (e: Throwable) {
            withContext(Dispatchers.IO) {
                dao.searchWordWithDefinition(query)?.toDto() ?: throw Exception(
                    "Не удалось найти слово в словаре",
                )
            }
        }
    }

    override suspend fun saveWordToDictionary(word: WordWithMeaningsDto) {
        withContext(Dispatchers.IO) {
            coroutineScope {
                val wordId = UUID.randomUUID()
                launch {
                    dao.saveWord(word.toEntity(id = wordId))
                    dao.saveMeanings(word.meanings.map { it.toEntity(wordId = wordId) })
                }
            }
        }
    }

    override suspend fun getWordById(wordId: String) = withContext(Dispatchers.IO) {
        val wordFromDatabase = dao.getWordById(wordId = UUID.fromString(wordId))
        return@withContext wordFromDatabase.toDto()
    }

    override suspend fun getWordsCount() = dao.getCount()
    override suspend fun getWordsCount(name: String) = dao.getCount(name)

    override suspend fun getSortedByCoefficientWords(limit: Int): List<WordTrainingDto> =
        withContext(Dispatchers.IO) {
            val wordsFromDatabase: List<WordWithDefinition> =
                dao.getWordsSortedByCoefficient(limit = limit)
            return@withContext wordsFromDatabase.map { wordFromDao ->
                wordFromDao.toTraining()
            }
        }

    override suspend fun getRandomWords(excludedName: String, limit: Int) =
        dao.getTwoRandomWordsExcludingByName(
            excludedName = excludedName,
            limit = limit
        ).map { entity ->
            entity.word
        }

    override fun getMockedVariants(): List<String> =
        MOCKED_VARIANTS

    override suspend fun updateWord(new: WordInfoDto) = withContext(Dispatchers.IO) {
        dao.updateWord(new.toEntity())
    }
}
