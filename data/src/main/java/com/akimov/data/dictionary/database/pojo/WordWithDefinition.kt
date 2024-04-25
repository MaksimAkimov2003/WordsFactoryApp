package com.akimov.data.dictionary.database.pojo

import androidx.room.Embedded
import androidx.room.Relation
import com.akimov.data.dictionary.database.entity.DefinitionEntity
import com.akimov.data.dictionary.database.entity.WordEntity

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
