package com.akimov.data.words.mapper

import com.akimov.data.words.database.entity.DefinitionEntity
import com.akimov.data.words.database.entity.KNOWLEDGE_COEFFICIENT_DEFAULT
import com.akimov.data.words.database.entity.WordEntity
import com.akimov.data.words.database.pojo.WordWithDefinition
import com.akimov.data.words.model.WordModel
import com.akimov.domain.dictionary.model.MeaningDto
import com.akimov.domain.dictionary.model.WordWithMeaningsDto
import com.akimov.domain.training.model.WordInfoDto
import com.akimov.domain.training.model.WordTrainingDto
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.util.UUID

fun List<WordModel>.toDto(): WordWithMeaningsDto {
    val word: WordModel = first()
    return WordWithMeaningsDto(
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

fun WordWithDefinition.toDto() =
    WordWithMeaningsDto(
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

fun WordWithMeaningsDto.toEntity(id: UUID) =
    WordEntity(
        word = word,
        transcription = transcription,
        soundUrl = soundUrl,
        partOfSpeech = partOfSpeech,
        id = id,
        knowledgeCoefficient = KNOWLEDGE_COEFFICIENT_DEFAULT,
    )

fun MeaningDto.toEntity(wordId: UUID) =
    DefinitionEntity(
        definition = definition,
        example = example,
        wordId = wordId,
    )

fun WordWithDefinition.toTraining() = WordTrainingDto(
    id = word.id.toString(),
    name = word.word,
    definitions = definitions.mapNotNull { entity ->
        entity.definition
    }
)

fun WordInfoDto.toEntity(): WordEntity = WordEntity(
    id = UUID.fromString(id),
    word = word,
    transcription = transcription,
    soundUrl = soundUrl,
    partOfSpeech = partOfSpeech,
    knowledgeCoefficient = knowledgeCoefficient,
)

fun WordEntity.toDto(): WordInfoDto = WordInfoDto(
    id = id.toString(),
    word = word,
    transcription = transcription,
    soundUrl = soundUrl,
    partOfSpeech = partOfSpeech,
    knowledgeCoefficient = knowledgeCoefficient,
)