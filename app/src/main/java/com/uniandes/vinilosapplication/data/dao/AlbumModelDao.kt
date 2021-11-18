package com.uniandes.vinilosapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinilosapplication.data.model.AlbumModel

@Dao
interface AlbumModelDao {
    @Query("SELECT * FROM albums_table")
    fun getAlbums(): List<AlbumModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: AlbumModel)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll(): Int
}