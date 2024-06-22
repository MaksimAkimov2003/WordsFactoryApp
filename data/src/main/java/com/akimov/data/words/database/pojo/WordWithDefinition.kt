package com.akimov.data.words.database.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.akimov.data.words.database.entity.DefinitionEntity
import com.akimov.data.words.database.entity.WordEntity

data class WordWithDefinition(
    @Embedded
    val word: WordEntity,

    @Relation(
        entity = DefinitionEntity::class,
        parentColumn = "id",
        entityColumn = "wordId"
    )
    val definitions: List<DefinitionEntity>
)
