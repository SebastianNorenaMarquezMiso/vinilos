package com.uniandes.vinilosapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinilosapplication.data.model.CollectorModel


@Dao
interface CollectorModelDao {
    @Query("SELECT * FROM collectors_table")
    fun getCollectors(): List<CollectorModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collector: CollectorModel)

    @Query("DELETE FROM collectors_table")
    suspend fun deleteAll(): Int
}