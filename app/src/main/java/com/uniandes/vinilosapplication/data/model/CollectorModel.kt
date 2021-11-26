package com.uniandes.vinilosapplication.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "collectors_table")
data class CollectorModel(
    @PrimaryKey var collectorId: Int?,
    var name: String?,
    var telephone: String?,
    var email: String?,
    @Ignore
    var collectorAlbums: List<AlbumModel>? = null
) {
    constructor() : this(0, null, null, null, null)
}