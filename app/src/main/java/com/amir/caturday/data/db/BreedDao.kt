package com.amir.caturday.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amir.caturday.data.db.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(breeds: List<BreedEntity>)

    @Query("SELECT * FROM tbl_breed")
    fun getBreeds(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM tbl_breed WHERE id = :id")
    fun getBreedById(id: String): Flow<BreedEntity>

    @Query("DELETE FROM tbl_breed")
    fun invalidateCache()
}
