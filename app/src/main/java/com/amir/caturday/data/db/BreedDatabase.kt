package com.amir.caturday.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amir.caturday.data.db.entity.BreedEntity

@Database(
    entities = [BreedEntity::class],
    version = 1,
)
abstract class BreedDatabase : RoomDatabase() {
    abstract val breedDao: BreedDao

    companion object {
        const val DATABASE_NAME = "db_breed"
    }
}
