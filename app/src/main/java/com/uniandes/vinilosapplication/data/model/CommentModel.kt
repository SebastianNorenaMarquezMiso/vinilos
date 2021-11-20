package com.uniandes.vinilosapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments_table")
data class CommentModel(
    val description: String,
    val rating: String,
    val albumId: Int,
    @PrimaryKey(autoGenerate = true)
    val commentId: Int = 0
)