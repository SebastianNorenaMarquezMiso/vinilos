package com.uniandes.vinilosapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uniandes.vinilosapplication.data.model.CommentModel

@Dao
interface CommentModelDao {
    @Query("SELECT * FROM comments_table WHERE albumId = :albumId ORDER BY rating DESC")
    fun getComments(albumId: Int): List<CommentModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comment: CommentModel)

    @Query("DELETE FROM comments_table")
    suspend fun clear(): Void

    @Query("DELETE FROM comments_table WHERE albumId = :albumId")
    suspend fun deleteAll(albumId: Int): Int
}