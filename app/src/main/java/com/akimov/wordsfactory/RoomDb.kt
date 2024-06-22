package com.akimov.wordsfactory

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akimov.data.words.database.dao.WordsDao
import com.akimov.data.words.database.entity.DefinitionEntity
import com.akimov.data.words.database.entity.WordEntity

@Database(
    version = 1,
    entities = [
        DefinitionEntity::class,
        WordEntity::class
    ],
)
abstract class RoomDb : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}