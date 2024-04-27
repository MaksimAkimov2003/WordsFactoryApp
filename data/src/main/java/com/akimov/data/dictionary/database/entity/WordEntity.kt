package com.akimov.data.dictionary.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

const val KNOWLEDGE_COEFFICIENT_DEFAULT = 0

@Entity("words")
data class WordEntity(
    @PrimaryKey
    val id: UUID,

    val word: String,
    val transcription: String?,
    val soundUrl: String?,
    val partOfSpeech: String?,

    val knowledgeCoefficient: Int,
)
