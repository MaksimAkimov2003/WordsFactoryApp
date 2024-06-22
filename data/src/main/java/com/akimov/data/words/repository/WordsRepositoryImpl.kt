package com.akimov.data.words.repository

import com.akimov.data.words.api.WordsService
import com.akimov.data.words.database.dao.WordsDao
import com.akimov.data.words.database.entity.DefinitionEntity
import com.akimov.data.words.database.entity.KNOWLEDGE_COEFFICIENT_DEFAULT
import com.akimov.data.words.database.entity.WordEntity
import com.akimov.data.words.database.pojo.WordWithDefinition
import com.akimov.data.words.mapper.toDto
import com.akimov.data.words.mapper.toEntity
import com.akimov.data.words.mapper.toTraining
import com.akimov.data.words.model.WordModel
import com.akimov.domain.dictionary.model.MeaningDto
import com.akimov.domain.dictionary.model.WordInfoDto
import com.akimov.domain.dictionary.repository.WordsRepository
import com.akimov.domain.training.model.WordTrainingDto
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class WordsRepositoryImpl(
    private val api: WordsService,
    private val dao: WordsDao,
) : WordsRepository {
    override suspend fun searchWord(query: String): WordInfoDto {
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

    override suspend fun saveWordToDictionary(word: WordInfoDto) {
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

    override suspend fun getWordsCount() = dao.getCount()

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

    override fun getMockedVariants() = listOf(
        "Smiling",
        "Freezing"
    )
}
