package com.uniandes.vinilosapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinilosapplication.data.model.MusicianModel

@Dao
interface MusicianModelDao {
    @Query("SELECT * FROM musicians_table")
    fun getMuscians(): List<MusicianModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(musician: MusicianModel)

    @Query("DELETE FROM musicians_table")
    suspend fun deleteAll(): Int
}