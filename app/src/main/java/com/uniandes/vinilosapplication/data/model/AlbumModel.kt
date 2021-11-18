package com.uniandes.vinilosapplication.data.model


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "albums_table")
data class AlbumModel(
    @PrimaryKey var albumId: Int?,
    var name: String?,
    var cover: String?,
    var releaseDate: String?,
    var description: String?,
    var genre: String?,
    var recordLabel: String?,
    @Ignore
    var tracks: List<TrackModel>?=null
) {
    constructor() : this(0, null, null, null, null, null, null, null)
}