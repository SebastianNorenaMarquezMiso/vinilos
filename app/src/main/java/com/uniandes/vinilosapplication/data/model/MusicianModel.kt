package com.uniandes.vinilosapplication.data.model

data class MusicianModel(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: List<AlbumModel>,
    val performerPrizes: List<PerformerPrizesModel>
)