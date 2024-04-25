package com.akimov.data.dictionary.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "definitions",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class DefinitionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val definition: String?,
    val example: String?,

    val wordId: UUID
)
