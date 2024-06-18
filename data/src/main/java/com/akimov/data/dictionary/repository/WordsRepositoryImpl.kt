package com.akimov.data.dictionary.repository

import com.akimov.data.dictionary.api.WordsService
import com.akimov.data.dictionary.database.dao.WordsDao
import com.akimov.data.dictionary.database.entity.DefinitionEntity
import com.akimov.data.dictionary.database.entity.KNOWLEDGE_COEFFICIENT_DEFAULT
import com.akimov.data.dictionary.database.entity.WordEntity
import com.akimov.data.dictionary.database.pojo.WordWithDefinition
import com.akimov.data.dictionary.model.WordModel
import com.akimov.domain.dictionary.model.MeaningDto
import com.akimov.domain.dictionary.model.WordInfoDto
import com.akimov.domain.dictionary.repository.WordsRepository
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

    private fun List<WordModel>.toDto(): WordInfoDto {
        val word: WordModel = first()
        return WordInfoDto(
            word = word.word,
            transcription = word.phonetic ?: word.phonetics.find { it.text != null }?.text,
            soundUrl = word.phonetics.find { (!it.audio.isNullOrEmpty()) }?.audio,
            partOfSpeech = word.meanings.firstOrNull()?.partOfSpeech,
            meanings =
                word.meanings.firstOrNull()?.definitions?.map { meaning ->
                    MeaningDto(
                        definition = meaning.definition,
                        example = meaning.example,
                    )
                }?.toImmutableList() ?: persistentListOf(),
        )
    }

    private fun WordWithDefinition.toDto() =
        WordInfoDto(
            word = word.word,
            transcription = word.transcription,
            soundUrl = word.soundUrl,
            partOfSpeech = word.partOfSpeech,
            meanings =
                definitions.map { meaning ->
                    MeaningDto(
                        definition = meaning.definition,
                        example = meaning.example,
                    )
                }.toImmutableList(),
        )

    private fun WordInfoDto.toEntity(id: UUID) =
        WordEntity(
            word = word,
            transcription = transcription,
            soundUrl = soundUrl,
            partOfSpeech = partOfSpeech,
            id = id,
            knowledgeCoefficient = KNOWLEDGE_COEFFICIENT_DEFAULT,
        )

    private fun MeaningDto.toEntity(wordId: UUID) =
        DefinitionEntity(
            definition = definition,
            example = example,
            wordId = wordId,
        )
}
