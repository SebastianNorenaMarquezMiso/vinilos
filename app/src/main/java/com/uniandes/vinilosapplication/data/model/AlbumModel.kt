package com.uniandes.vinilosapplication.data.model

data class AlbumModel(
    val albumId: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String
)