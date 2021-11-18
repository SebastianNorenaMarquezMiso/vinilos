package com.uniandes.vinilosapplication.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "musicians_table")
data class MusicianModel(
    @PrimaryKey var id: Int?,
    var name: String?,
    var image: String?,
    var description: String?,
    var birthDate: String?,
    @Ignore
    var albums: List<AlbumModel>?,
    @Ignore
    var performerPrizes: List<PerformerPrizesModel>?
){
    constructor() : this(0, null, null, null, null, null, null)
}